package com.gestaoensino.gestao_ensino.api.dtos.input;

import com.gestaoensino.gestao_ensino.domain.enums.EAnoLetivo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TurmaDynamoInput {

    private Integer id;
    private EAnoLetivo anoLetivo;
    private List<AlunoInput> alunos;


}
