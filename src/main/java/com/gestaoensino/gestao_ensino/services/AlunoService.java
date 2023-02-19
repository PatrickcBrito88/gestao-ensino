package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;

import java.util.List;

public interface AlunoService {

    Aluno salvarAluno (Aluno aluno);
    Aluno editarAluno(Aluno aluno, String id);
    void apagarAluno(String id);
    Aluno buscarAluno(String id);
    List<Aluno> listarAlunos();
}
