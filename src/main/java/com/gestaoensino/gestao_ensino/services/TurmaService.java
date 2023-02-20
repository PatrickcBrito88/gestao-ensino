package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;

import java.util.List;

public interface TurmaService {

    Turma cadastrarTurma (Turma turma);

    Turma buscarTurma(Integer idTurma);

//    void adicionarDisciplina (Long idDisciplina, Long idTurma);
//
//    void adicionarAluno (Long idAluno, Long idTurma);

    List<Turma> listarTurmas();

//    void removerDisciplina (Long idDisciplina, Long idTurma);
//
//    void removerAluno (Long idAluno, Long idTurma);

    void apagarTurma(Long id);

    Turma editarDisciplina(String nome, Integer id);

}
