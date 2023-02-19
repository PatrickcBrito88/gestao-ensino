package com.gestaoensino.gestao_ensino.domain.model.dynamo.modelagemDynamo;

import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;

import java.util.List;

public class AlunoDynamo extends Aluno {

    private List<AvaliacoesDynamo> avaliacoes;
}
