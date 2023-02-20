package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.input.EscolaInput;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.EscolaCorrente;

public interface EscolaCorrenteService {

    EscolaCorrente salvar(EscolaInput escolaInput);
}
