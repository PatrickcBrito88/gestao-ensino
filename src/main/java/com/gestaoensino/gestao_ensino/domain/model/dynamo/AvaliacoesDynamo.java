package com.gestaoensino.gestao_ensino.domain.model.dynamo;

import com.gestaoensino.gestao_ensino.domain.enums.EPeriodo;
import lombok.Data;

import java.util.List;

@Data
public class AvaliacoesDynamo {

    private EPeriodo periodo;
    private List<Double> nota;

    private Double media;
}
