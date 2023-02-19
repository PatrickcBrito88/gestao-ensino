package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gestaoensino.gestao_ensino.domain.enums.EAnoLetivo;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import lombok.Data;

import java.util.List;

@DynamoDBTable(tableName = "gestao_ensino_ano_letivo")
@Data
public class TurmaDynamo extends Turma {

    private List<AlunoDynamo> alunos;
    @DynamoDBTypeConvertedEnum
    private EAnoLetivo anoLetivo;

}
