package com.example.demo;

import com.example.demo.Signature;
import com.example.demo.SignatureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SignatureRepository extends JpaRepository<Signature, UUID> {

    List<Signature> findByStatus(SignatureStatus status);

    List<Signature> findByUpdatedAtAfter(LocalDateTime since);

    List<Signature> findByIdIn(List<UUID> ids);
}