package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "signature_history")
public class SignatureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private java.util.UUID signatureId;

    private LocalDateTime versionCreatedAt;
    private String threatName;
    private String firstBytes;
    private String remainderHash;
    private int remainderLength;
    private String fileType;
    private int offsetStart;
    private int offsetEnd;
    @Lob
    private String digitalSignature;
    private SignatureStatus status;
    private LocalDateTime updatedAt;

    public void setSignatureId(UUID signatureId) { this.signatureId = signatureId; }
    public void setVersionCreatedAt(LocalDateTime versionCreatedAt) { this.versionCreatedAt = versionCreatedAt; }
    public void setThreatName(String threatName) { this.threatName = threatName; }
    public void setFirstBytes(String firstBytes) { this.firstBytes = firstBytes; }
    public void setRemainderHash(String remainderHash) { this.remainderHash = remainderHash; }
    public void setRemainderLength(int remainderLength) { this.remainderLength = remainderLength; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public void setOffsetStart(int offsetStart) { this.offsetStart = offsetStart; }
    public void setOffsetEnd(int offsetEnd) { this.offsetEnd = offsetEnd; }
    public void setDigitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }
    public void setStatus(SignatureStatus status) { this.status = status; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}