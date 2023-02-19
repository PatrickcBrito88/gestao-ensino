package com.gestaoensino.gestao_ensino.domain.enums;

public enum EAnoLetivo {

    ANO_2023(1, "2023");

    private final Integer id;
    private final String nome;

    EAnoLetivo(Integer id, String nome) {
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