package com.example.demo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "signatures")
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objectName;

    @Column(nullable = false)
    private byte[] first8Bytes;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private int remainingLength;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private int startOffset;

    @Column(nullable = false)
    private int endOffset;

    @ManyToMany(mappedBy = "signatures")
    private List<Task> tasks;

    // Геттеры и сеттеры
}

