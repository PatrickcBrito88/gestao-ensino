package com.gestaoensino.gestao_ensino.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AlunoDTO {

    @NotBlank(message = "aluno.nomeCompleto.vazio")
    private String nomeCompleto;
    @NotBlank(message = "aluno.telefoneResponsavel.vazio")
    private String telefoneResponsavel;
    @NotNull(message = "aluno.dataNascimento.vazio")
    private Date dataNascimento;
}
