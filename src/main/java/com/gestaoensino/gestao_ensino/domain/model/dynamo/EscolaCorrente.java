package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;
import lombok.Data;

import java.util.List;

@DynamoDBTable(tableName = "gestao_ensino_ano_letivo")
@Data
@DynamoDBDocument
public class EscolaCorrente extends Escola {

    private List<TurmaDynamo> turmas;
}
