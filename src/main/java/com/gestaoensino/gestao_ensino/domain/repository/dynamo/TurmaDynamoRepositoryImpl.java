package com.gestaoensino.gestao_ensino.domain.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;
import org.springframework.stereotype.Repository;

@Repository
public class TurmaDynamoRepositoryImpl {

    private final DynamoDBMapper dynamoDBMapper;

    public TurmaDynamoRepositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public TurmaDynamo save(TurmaDynamo turmaDynamo) {
        dynamoDBMapper.save(turmaDynamo);
        return turmaDynamo;
    }
}
