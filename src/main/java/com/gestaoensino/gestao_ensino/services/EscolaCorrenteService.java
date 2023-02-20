package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.dynamo.EscolaCorrente;

public interface EscolaCorrenteService {
    
    EscolaCorrente salvarEscola (EscolaCorrente escolaCorrente);
}
