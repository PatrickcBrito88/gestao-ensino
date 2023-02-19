package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import lombok.Data;

import java.util.List;

@Data
public class DisciplinaDynamo extends Disciplina {

    private ProfessorDynamo professor;
    private List<AvaliacoesDynamo> avaliacoes;
}
