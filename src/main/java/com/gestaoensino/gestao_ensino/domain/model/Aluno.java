package com.gestaoensino.gestao_ensino.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String telefoneResponsavel;
    private Date dataNascimento;
    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = true)
    private Turma turma;

}
