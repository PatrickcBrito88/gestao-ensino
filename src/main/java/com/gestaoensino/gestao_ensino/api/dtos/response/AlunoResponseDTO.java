package com.gestaoensino.gestao_ensino.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AlunoResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String telefoneResponsavel;
    private Date dataNascimento;
}
