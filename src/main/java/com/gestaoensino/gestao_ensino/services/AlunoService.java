package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;

import java.util.List;

public interface AlunoService {

    Aluno salvarAluno (AlunoDTO alunoDto);
    Aluno editarAluno(Aluno aluno, String id);
    void apagarAluno(String id);
    Aluno buscarAluno(String id);
    List<Aluno> listarAlunos();
}
