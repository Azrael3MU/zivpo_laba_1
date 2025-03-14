package com.example.controller;

import com.example.demo.Signature;
import com.example.service.SignatureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signatures")
public class SignatureController {
    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PostMapping
    public Signature createSignature(@RequestBody Signature signature) {
        return signatureService.createSignature(signature);
    }
}
