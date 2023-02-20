package com.gestaoensino.gestao_ensino.domain.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.EscolaCorrente;
import com.gestaoensino.gestao_ensino.services.EscolaCorrenteService;

public class EscolaCorrenteImpl implements EscolaCorrenteService {

    private final DynamoDBMapper dynamoDBMapper;

    public EscolaCorrenteImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public EscolaCorrente salvarEscola(EscolaCorrente escolaCorrente) {
        return null;
    }
}
