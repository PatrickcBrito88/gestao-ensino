package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.Professor;

import java.util.List;

public interface ProfessorService {

    Professor salvarProfessor (Professor professor);
    Professor editarProfessor(Professor professor, Long id);
    void apagarProfessor(Long id);
    Professor buscarProfessor(Long id);
    List<Professor> listarProfessores();
}
