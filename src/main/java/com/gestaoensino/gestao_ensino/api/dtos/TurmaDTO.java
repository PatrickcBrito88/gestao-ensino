package com.gestaoensino.gestao_ensino.api.dtos;

import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TurmaDTO {

    private Long id;
    private Set<Disciplina> disciplinas = new HashSet<>();
    private Set<Aluno> alunos = new HashSet<>();
    private String nome;

}
