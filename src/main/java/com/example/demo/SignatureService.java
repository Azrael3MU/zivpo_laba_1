package com.example.demo;

import com.example.demo.Signature;
import com.example.demo.SignatureStatus;
import com.example.demo.SignatureRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignatureService {

    private final SignatureRepository repository;
    private final SignatureHistoryRepository historyRepo;
    private final SignatureAuditRepository auditRepo;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public SignatureService(SignatureRepository repository,
                            SignatureHistoryRepository historyRepo,
                            SignatureAuditRepository auditRepo,
                            PrivateKey privateKey,
                            PublicKey publicKey) {
        this.repository = repository;
        this.historyRepo = historyRepo;
        this.auditRepo = auditRepo;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Signature addSignature(Signature signature) throws Exception {
        signature.setUpdatedAt(LocalDateTime.now());
        signature.setStatus(SignatureStatus.ACTUAL);
        String dataToSign = buildDataToSign(signature);
        String signatureValue = sign(dataToSign);
        signature.setDigitalSignature(signatureValue);
        return repository.save(signature);
    }

    public Optional<Signature> getById(UUID id) {
        return repository.findById(id);
    }

    public List<Signature> getAllActual() {
        return repository.findByStatus(SignatureStatus.ACTUAL);
    }

    public List<Signature> getDiffSince(LocalDateTime since) {
        return repository.findByUpdatedAtAfter(since);
    }

    public List<Signature> getByIds(List<UUID> ids) {
        return repository.findByIdIn(ids);
    }

    public void markAsDeleted(UUID id) {
        repository.findById(id).ifPresent(sig -> {
            sig.setStatus(SignatureStatus.DELETED);
            sig.setUpdatedAt(LocalDateTime.now());
            repository.save(sig);
        });
    }

    public boolean verifySignature(Signature signature) throws Exception {
        String data = buildDataToSign(signature);
        java.security.Signature verifier = java.security.Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(data.getBytes());
        return verifier.verify(Base64.getDecoder().decode(signature.getDigitalSignature()));
    }

    private String buildDataToSign(Signature s) {
        return s.getThreatName() + s.getFirstBytes() + s.getRemainderHash() +
                s.getRemainderLength() + s.getFileType() +
                s.getOffsetStart() + s.getOffsetEnd();
    }

    private String sign(String data) throws Exception {
        java.security.Signature signer = java.security.Signature.getInstance("SHA256withRSA");
        signer.initSign(privateKey);
        signer.update(data.getBytes());
        byte[] sigBytes = signer.sign();
        return Base64.getEncoder().encodeToString(sigBytes);
    }

    public Signature updateSignature(UUID id, Signature updatedData, String changedBy) throws Exception {
        Signature existing = repository.findById(id).orElseThrow();

        // Сохраняем в историю
        SignatureHistory history = new SignatureHistory();
        history.setSignatureId(existing.getId());
        history.setVersionCreatedAt(LocalDateTime.now());
        history.setThreatName(existing.getThreatName());
        history.setFirstBytes(existing.getFirstBytes());
        history.setRemainderHash(existing.getRemainderHash());
        history.setRemainderLength(existing.getRemainderLength());
        history.setFileType(existing.getFileType());
        history.setOffsetStart(existing.getOffsetStart());
        history.setOffsetEnd(existing.getOffsetEnd());
        history.setDigitalSignature(existing.getDigitalSignature());
        history.setStatus(existing.getStatus());
        history.setUpdatedAt(existing.getUpdatedAt());
        historyRepo.save(history);

        // Обновляем текущую запись
        existing.setThreatName(updatedData.getThreatName());
        existing.setFirstBytes(updatedData.getFirstBytes());
        existing.setRemainderHash(updatedData.getRemainderHash());
        existing.setRemainderLength(updatedData.getRemainderLength());
        existing.setFileType(updatedData.getFileType());
        existing.setOffsetStart(updatedData.getOffsetStart());
        existing.setOffsetEnd(updatedData.getOffsetEnd());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setStatus(SignatureStatus.ACTUAL);
        existing.setDigitalSignature(sign(buildDataToSign(existing)));
        repository.save(existing);

        // Запись в аудит
        SignatureAudit audit = new SignatureAudit();
        audit.setSignatureId(existing.getId());
        audit.setChangedBy(changedBy);
        audit.setChangeType("UPDATED");
        audit.setChangedAt(LocalDateTime.now());
        audit.setFieldsChanged("all fields"); // можешь детализировать, если надо
        auditRepo.save(audit);

        return existing;
    }
    @Scheduled(cron = "0 0 0 * * *") // каждый день в полночь
    public void verifyAllSignatures() {
        List<Signature> all = repository.findAll();
        for (Signature s : all) {
            try {
                if (!verifySignature(s)) {
                    s.setStatus(SignatureStatus.CORRUPTED);
                    s.setUpdatedAt(LocalDateTime.now());
                    repository.save(s);

                    SignatureAudit audit = new SignatureAudit();
                    audit.setSignatureId(s.getId());
                    audit.setChangedBy("SYSTEM");
                    audit.setChangeType("AUTO_VERIFICATION_FAIL");
                    audit.setChangedAt(LocalDateTime.now());
                    audit.setFieldsChanged("status -> CORRUPTED");
                    auditRepo.save(audit);
                }
            } catch (Exception e) {
                // логировать если нужно
            }
        }
    }
}