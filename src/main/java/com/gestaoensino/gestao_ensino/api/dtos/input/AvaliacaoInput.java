package com.gestaoensino.gestao_ensino.api.dtos.input;

import com.gestaoensino.gestao_ensino.domain.enums.EPeriodo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AvaliacaoInput {

    private EPeriodo periodo;
    private List<Double> notas;
    private Double media;
}
