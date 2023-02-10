package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;

import java.util.List;

public interface AlunoService {

    Aluno salvarAluno (AlunoDTO alunoDto);
    Aluno editarAluno(Aluno aluno, Long id);
    void apagarAluno(Long id);
    Aluno buscarAluno(Long id);
    List<Aluno> listarAlunos();
}
