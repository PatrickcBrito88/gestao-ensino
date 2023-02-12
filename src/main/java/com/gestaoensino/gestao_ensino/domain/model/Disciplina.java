package com.gestaoensino.gestao_ensino.domain.model;

import lombok.Data;

import javax.persistence.*;

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
