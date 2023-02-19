package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import lombok.Data;

import java.util.List;

@Data
public class DisciplinaDynamo extends Disciplina {

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = ProfessorDynamo.class)
    private ProfessorDynamo professor;
    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = AvaliacoesDynamo.class)
    private List<AvaliacoesDynamo> avaliacoes;
}
