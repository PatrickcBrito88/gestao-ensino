package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import lombok.Data;

import java.util.List;

@Data
public class AlunoDynamo extends Aluno {

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = DisciplinaDynamo.class)
    private List<DisciplinaDynamo> disciplinas;
}
