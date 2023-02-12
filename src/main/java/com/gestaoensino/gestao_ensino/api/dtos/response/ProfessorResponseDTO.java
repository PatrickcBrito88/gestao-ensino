package com.gestaoensino.gestao_ensino.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String nomeComum;
    private String email;
    private String telefone;
}
