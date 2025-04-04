package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "signature_audit")
public class SignatureAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    private java.util.UUID signatureId;
    private String changedBy;
    private String changeType;
    private LocalDateTime changedAt;

    @Column(columnDefinition = "TEXT")
    private String fieldsChanged;

    public void setSignatureId(UUID signatureId) { this.signatureId = signatureId; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
    public void setFieldsChanged(String fieldsChanged) { this.fieldsChanged = fieldsChanged; }
}

