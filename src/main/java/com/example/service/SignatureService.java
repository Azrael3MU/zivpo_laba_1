package com.example.service;

import com.example.demo.Signature;
import com.example.repository.SignatureRepository;
import org.springframework.stereotype.Service;

@Service
public class SignatureService {
    private final SignatureRepository signatureRepository;

    public SignatureService(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    public Signature createSignature(Signature signature) {
        return signatureRepository.save(signature);
    }
}

