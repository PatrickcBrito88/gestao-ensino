package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;

import java.util.List;

public interface ProfessorService {

    Professor salvarProfessor (Professor professor);
    Professor editarProfessor(Professor professor, Integer id);
    void apagarProfessor(Integer id);
    Professor buscarProfessor(Integer id);
    List<Professor> listarProfessores();
}
