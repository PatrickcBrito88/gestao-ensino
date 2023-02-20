package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBDocument
public class AlunoDynamo extends Aluno {

    @DynamoDBAttribute
    private List<DisciplinaDynamo> disciplinas;
}
