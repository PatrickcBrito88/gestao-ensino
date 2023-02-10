package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;

import java.util.List;

public interface DisciplinaService {

    Disciplina salvarDisciplina (DisciplinaDTO disciplinaDTO);
    Disciplina editarDisciplina(Disciplina disciplina, Long id);
    void apagarDisciplina(Long id);
    Disciplina buscarDisciplina(Long id);
    List<Disciplina> listarDisciplinas();

}
