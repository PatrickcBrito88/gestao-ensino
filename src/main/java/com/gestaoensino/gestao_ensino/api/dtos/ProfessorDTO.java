package com.gestaoensino.gestao_ensino.api.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfessorDTO {

    @NotBlank(message = "{professor.nomeCompleto.vazio}")
    private String nomeCompleto;
    @NotBlank(message = "{professor.nomeComum.vazio}")
    private String nomeComum;
    @NotBlank(message = "{professor.email.vazio}")
    @Email
    private String email;
    @NotBlank(message = "{professor.telefone.vazio}")
    private String telefone;
}
