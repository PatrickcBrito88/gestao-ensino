package com.gestaoensino.gestao_ensino.domain.model.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.converter.LocalDateTypeConverter;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDate;

@Data
@RedisHash("GestaoEnsino_Aluno")
@DynamoDBDocument
public class Aluno {

    @Id
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "alunoId-index")
    @DynamoDBAttribute
    private Integer id;
    @Indexed
    @DynamoDBAttribute
    private String nomeCompleto;
    @DynamoDBAttribute
    private String telefoneResponsavel;
    @Indexed
    @DynamoDBTypeConverted(converter = LocalDateTypeConverter.class)
    private LocalDate dataNascimento;

}
