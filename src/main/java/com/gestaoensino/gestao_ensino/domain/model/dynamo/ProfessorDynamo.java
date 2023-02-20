package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import lombok.Data;

@Data
@DynamoDBDocument
public class ProfessorDynamo extends Professor {

}
