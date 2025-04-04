package com.example.demo;

import com.example.demo.SignatureHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SignatureHistoryRepository extends JpaRepository<SignatureHistory, Long> {
    List<SignatureHistory> findBySignatureId(java.util.UUID signatureId);
}