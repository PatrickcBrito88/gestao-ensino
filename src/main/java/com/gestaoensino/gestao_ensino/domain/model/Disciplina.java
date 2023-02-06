package com.gestaoensino.gestao_ensino.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = true)
    private Turma turma;
}
