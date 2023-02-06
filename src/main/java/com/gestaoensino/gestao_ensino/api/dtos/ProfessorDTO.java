package com.gestaoensino.gestao_ensino.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDTO {

    private String nomeCompleto;
    private String nomeComum;
    private String email;
    private String telefone;
}
