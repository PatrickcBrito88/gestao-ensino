package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBDocument
public class DisciplinaDynamo extends Disciplina {

    @DynamoDBAttribute
    private ProfessorDynamo professor;
    @DynamoDBAttribute
    private List<AvaliacoesDynamo> avaliacoes;
}
