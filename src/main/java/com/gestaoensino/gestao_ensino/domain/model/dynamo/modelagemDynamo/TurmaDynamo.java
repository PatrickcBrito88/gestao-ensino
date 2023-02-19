package com.gestaoensino.gestao_ensino.domain.model.dynamo.modelagemDynamo;

import com.gestaoensino.gestao_ensino.domain.enums.EAnoLetivo;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;

import java.util.List;

public class TurmaDynamo extends Turma {

    private List<DisciplinaDynamo> disciplinas;
    private EAnoLetivo anoLetivo;

}
