package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.input.TurmaDynamoInput;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;

public interface TurmaDynamoService {

    TurmaDynamo salvarTurma(TurmaDynamoInput turmaDynamoInput);
}
