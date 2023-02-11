package com.gestaoensino.gestao_ensino.api.dtos.com_id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorComIdDTO {

    private Long id;
    private String nomeCompleto;
    private String nomeComum;
    private String email;
    private String telefone;
}
