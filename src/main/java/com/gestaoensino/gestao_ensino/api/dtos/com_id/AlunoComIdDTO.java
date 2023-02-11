package com.gestaoensino.gestao_ensino.api.dtos.com_id;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AlunoComIdDTO {

    private Long id;
    private String nomeCompleto;
    private String telefoneResponsavel;
    private Date dataNascimento;
}
