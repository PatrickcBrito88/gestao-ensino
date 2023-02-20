package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;

import java.util.List;

public interface EscolaService {

    Escola cadastrarEscola (Escola escola);
    Escola buscarEscola(Integer idEscola);
    List<Escola> listarEscolas();

}
