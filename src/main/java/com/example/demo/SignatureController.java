package com.example.demo;

import com.example.demo.Signature;
import com.example.demo.SignatureService;
import com.example.demo.SignatureAuditRepository;
import com.example.demo.SignatureHistoryRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/signatures")
public class SignatureController {

    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Signature> updateSignature(
            @PathVariable UUID id,
            @RequestBody Signature updatedData,
            @RequestParam String changedBy) throws Exception {
        Signature result = signatureService.updateSignature(id, updatedData, changedBy);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Signature> create(@RequestBody Signature signature) throws Exception {
        Signature saved = signatureService.addSignature(signature);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Signature>> getAllActual() {
        return ResponseEntity.ok(signatureService.getAllActual());
    }

    @GetMapping("/diff")
    public ResponseEntity<List<Signature>> getDiff(@RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        return ResponseEntity.ok(signatureService.getDiffSince(since));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signature> getById(@PathVariable UUID id) {
        Optional<Signature> signature = signatureService.getById(id);
        return signature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/byIds")
    public ResponseEntity<List<Signature>> getByIds(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(signatureService.getByIds(ids));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        signatureService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}
