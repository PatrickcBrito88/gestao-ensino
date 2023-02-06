package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.TurmaDTO;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;

import java.util.List;

public interface TurmaService {

    TurmaDTO cadastrarTurma (TurmaDTO turmaDTO);

    TurmaDTO adicionarDisciplina (Disciplina disciplina);

    TurmaDTO adicionarAluno (Aluno aluno);

    List<TurmaDTO> listarTurmas();

    TurmaDTO removerDisciplina (Disciplina disciplina);

    TurmaDTO removerAluno (Aluno aluno);

    void apagarTurma(Long id);

}
