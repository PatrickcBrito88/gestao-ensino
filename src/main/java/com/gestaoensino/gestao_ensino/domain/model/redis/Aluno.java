package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.converter.LocalDateTypeConverter;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@RedisHash("GestaoEnsino_Aluno")
@DynamoDBDocument
public class Aluno {

    @Id
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "alunoId-index")
    private Integer id;
    @Indexed
    private String nomeCompleto;
    private String telefoneResponsavel;
    @Indexed
    @DynamoDBTypeConverted(converter = LocalDateTypeConverter.class)
    private Date dataNascimento;

}
