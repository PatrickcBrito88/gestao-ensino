package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("GestaoEnsino_Professor")
@DynamoDBDocument
public class Professor {

    @Id
    private Integer id;
    private String nomeCompleto;
    private String nomeComum;
    private String email;
    private String telefone;
}
