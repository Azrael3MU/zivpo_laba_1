package com.example.demo;

import com.example.demo.SignatureAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SignatureAuditRepository extends JpaRepository<SignatureAudit, Long> {
    List<SignatureAudit> findBySignatureId(java.util.UUID signatureId);
}
