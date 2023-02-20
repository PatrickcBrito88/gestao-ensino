package com.gestaoensino.gestao_ensino.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolaDTO {

    private String nome;
    private String endereco;
    private String telefone;
    private String nomeDiretor;
    private String telefoneDiretor;
}
