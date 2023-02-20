package com.gestaoensino.gestao_ensino.domain.enums;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public enum EPeriodo {

    PRIMEIRO_TRIMESTRE(1, "1º TRIMESTRE"),
    SEGUNDO_TRIMESTRE(2, "2º TRIMESTRE"),
    TERCEIRO_TRIMESTRE(3, "3º TRIMESTRE");

    private final Integer id;
    private final String nome;

    EPeriodo(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
