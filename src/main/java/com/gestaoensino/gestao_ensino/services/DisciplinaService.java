package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;

import java.util.List;

public interface DisciplinaService {

    DisciplinaDTO salvarDisciplina (DisciplinaDTO disciplinaDTO);
    DisciplinaDTO editarDisciplina(Disciplina disciplina, Long id);
    void apagarDisciplina(Long id);
    DisciplinaDTO buscarDisciplina(Long id);
    List<DisciplinaDTO> listarDisciplinas();

}
