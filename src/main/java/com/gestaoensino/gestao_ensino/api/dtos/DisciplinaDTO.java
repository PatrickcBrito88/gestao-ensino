package com.gestaoensino.gestao_ensino.api.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DisciplinaDTO {

    @NotBlank(message = "{disciplina.nome.vazio}")
    private String nome;
}
