package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
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
    @DynamoDBHashKey
    private Integer id;
    private String nome;
    @DynamoDBTypeConvertedEnum
    private ESerie serie;

}
