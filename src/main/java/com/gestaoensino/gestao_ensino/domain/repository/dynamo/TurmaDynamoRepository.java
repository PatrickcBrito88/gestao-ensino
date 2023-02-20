package com.gestaoensino.gestao_ensino.domain.repository.dynamo;

import com.gestaoensino.gestao_ensino.api.dtos.input.TurmaDynamoInput;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;


public interface TurmaDynamoRepository  {

    TurmaDynamo salvar (TurmaDynamoInput turmaDynamoInput);

}
