package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gestaoensino.gestao_ensino.domain.enums.ESerie;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("GestaoEnsino_Turma")
@DynamoDBDocument
public class Turma {

    @Id
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "turmaId-index")
    private Integer id;
    private String nome;
    @DynamoDBTypeConvertedEnum
    private ESerie serie;

}
