package com.gestaoensino.gestao_ensino.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AlunoDTO {

    private String nomeCompleto;
    private String telefoneResponsavel;
    private Date dataNascimento;
}
