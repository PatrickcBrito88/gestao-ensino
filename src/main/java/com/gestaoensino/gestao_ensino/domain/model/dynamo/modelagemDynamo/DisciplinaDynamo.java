package com.gestaoensino.gestao_ensino.domain.model.dynamo.modelagemDynamo;

import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;

import java.util.List;

public class DisciplinaDynamo extends Disciplina {

    private ProfessorDynamo professor;
    private List<AlunoDynamo> alunos;
}
