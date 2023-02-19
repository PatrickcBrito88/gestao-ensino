package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;

import java.util.List;

public interface ProfessorService {

    Professor salvarProfessor (Professor professor);
    Professor editarProfessor(Professor professor, String id);
    void apagarProfessor(String id);
    Professor buscarProfessor(String id);
    List<Professor> listarProfessores();
}
