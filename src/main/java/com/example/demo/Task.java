package com.example.demo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "task_signatures",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "signature_id")
    )
    private List<Signature> signatures;

    // Геттеры и сеттеры
}

