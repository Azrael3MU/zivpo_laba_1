package com.example.demo;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "signatures")
public class Signature {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "threat_name", nullable = false)
    private String threatName;

    @Column(name = "first_bytes", length = 16, nullable = false)
    private String firstBytes;

    @Column(name = "remainder_hash", nullable = false)
    private String remainderHash;

    @Column(name = "remainder_length", nullable = false)
    private int remainderLength;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "offset_start", nullable = false)
    private int offsetStart;

    @Column(name = "offset_end", nullable = false)
    private int offsetEnd;

    @Lob
    @Column(name = "digital_signature", nullable = false)
    private String digitalSignature;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SignatureStatus status;

    // Constructors, getters and setters

    public Signature() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getThreatName() {
        return threatName;
    }

    public void setThreatName(String threatName) {
        this.threatName = threatName;
    }

    public String getFirstBytes() {
        return firstBytes;
    }

    public void setFirstBytes(String firstBytes) {
        this.firstBytes = firstBytes;
    }

    public String getRemainderHash() {
        return remainderHash;
    }

    public void setRemainderHash(String remainderHash) {
        this.remainderHash = remainderHash;
    }

    public int getRemainderLength() {
        return remainderLength;
    }

    public void setRemainderLength(int remainderLength) {
        this.remainderLength = remainderLength;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getOffsetStart() {
        return offsetStart;
    }

    public void setOffsetStart(int offsetStart) {
        this.offsetStart = offsetStart;
    }

    public int getOffsetEnd() {
        return offsetEnd;
    }

    public void setOffsetEnd(int offsetEnd) {
        this.offsetEnd = offsetEnd;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SignatureStatus getStatus() {
        return status;
    }

    public void setStatus(SignatureStatus status) {
        this.status = status;
    }
}

