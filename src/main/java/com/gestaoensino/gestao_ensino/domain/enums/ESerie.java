package com.gestaoensino.gestao_ensino.domain.enums;

public enum ESerie {

    PRIMEIRO_ANO(1, "1º Ano"),
    SEGUNDO_ANO(2, "2º Ano"),
    TERCEIRO_ANO(3, "3º Ano"),
    QUARTO_ANO(4, "4º Ano"),
    QUINTO_ANO(5, "5º Ano"),
    SEXTO_ANO(6, "6º Ano"),
    SETIMO_ANO(7, "7º Ano"),
    OITAVO_ANO(8, "8º Ano"),
    NONO_ANO(9, "9º Ano");

    private final Integer id;
    private final String nome;

    ESerie(Integer id, String nome) {
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
