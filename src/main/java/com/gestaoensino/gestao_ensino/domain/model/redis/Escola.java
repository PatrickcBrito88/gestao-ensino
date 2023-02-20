package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("GestaoEnsino_Escola")
@DynamoDBDocument
public class Escola {

    @Id
    @DynamoDBHashKey
    private Integer id;
    private String nome;
    private String endereco;
    private String telefone;
    private String nomeDiretor;
    private String telefoneDiretor;
}
