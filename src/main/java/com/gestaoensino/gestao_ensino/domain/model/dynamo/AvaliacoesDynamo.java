package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.gestaoensino.gestao_ensino.domain.enums.EPeriodo;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBDocument
public class AvaliacoesDynamo {

    @DynamoDBAttribute
    private EPeriodo periodo;
    @DynamoDBAttribute
    private List<Double> notas;
    @DynamoDBAttribute
    private Double media;
}
