package com.gestaoensino.gestao_ensino.domain.model.dynamo.modelagemDynamo;

import com.gestaoensino.gestao_ensino.domain.enums.EPeriodo;

import java.util.List;

public class AvaliacoesDynamo {

    private String id;
    private List<Double> nota;
    private EPeriodo periodo;
    private Double media;
}
