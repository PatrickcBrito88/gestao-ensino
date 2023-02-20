package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;

import java.util.List;

public interface AlunoService {

    Aluno salvarAluno (Aluno aluno);
    Aluno editarAluno(Aluno aluno, Integer id);
    void apagarAluno(Integer id);
    Aluno buscarAluno(Integer id);
    List<Aluno> listarAlunos();
}
