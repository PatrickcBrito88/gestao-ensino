package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import lombok.Data;

import java.util.List;

@Data
public class AlunoDynamo extends Aluno {

    private List<DisciplinaDynamo> disciplinas;
}
