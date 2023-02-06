package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;

import java.util.List;

public interface AlunoService {

    AlunoDTO salvarAluno (AlunoDTO alunoDto);
    AlunoDTO editarAluno(Aluno aluno, Long id);
    void apagarAluno(Long id);
    AlunoDTO buscarAluno(Long id);
    List<AlunoDTO> listarAlunos();
}
