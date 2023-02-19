package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("GestaoEnsino_Disciplina")
@DynamoDBDocument
public class Disciplina {

    @Id
    private Long id;
    private String nome;
}
