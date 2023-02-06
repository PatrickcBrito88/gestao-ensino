package com.gestaoensino.gestao_ensino.api.dtos;

import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TurmaDTO {

    private Long id;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Aluno> alunos = new ArrayList<>();

}
