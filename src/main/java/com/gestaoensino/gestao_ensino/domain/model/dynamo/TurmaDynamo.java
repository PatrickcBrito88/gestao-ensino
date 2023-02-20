package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gestaoensino.gestao_ensino.domain.enums.EAnoLetivo;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import lombok.Data;

import java.util.List;


@Data
@DynamoDBDocument
public class TurmaDynamo extends Turma {

    @DynamoDBAttribute
    private List<AlunoDynamo> alunos;
    @DynamoDBRangeKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "turmaId-anoLetivo-index")
    @DynamoDBTypeConvertedEnum
    private EAnoLetivo anoLetivo;

}
