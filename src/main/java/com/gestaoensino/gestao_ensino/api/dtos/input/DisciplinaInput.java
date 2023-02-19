package com.gestaoensino.gestao_ensino.api.dtos.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisciplinaInput {

    private Integer id;
    private List<AvaliacaoInput> avaliacoes;

}
