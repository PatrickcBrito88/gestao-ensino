package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;

import java.util.List;

public interface DisciplinaService {

    Disciplina salvarDisciplina (DisciplinaDTO disciplinaDTO);
    Disciplina editarDisciplina(Disciplina disciplina, String id);
    void apagarDisciplina(String id);
    Disciplina buscarDisciplina(String id);
    List<Disciplina> listarDisciplinas();

}
