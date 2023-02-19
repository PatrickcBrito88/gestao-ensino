package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "User")
public class AnoLetivo {

    private String id;
    private List<Turma> turmas;
}
