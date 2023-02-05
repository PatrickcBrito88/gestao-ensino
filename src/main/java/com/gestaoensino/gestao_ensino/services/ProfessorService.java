package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDto;
import com.gestaoensino.gestao_ensino.domain.model.Professor;

import java.util.List;

public interface ProfessorService {

    ProfessorDto salvarProfessor (ProfessorDto professorDto);
    ProfessorDto editarProfessor(Professor professor, Long id);
    void apagarProfessor(Long id);
    ProfessorDto buscarProfessor(Long id);
    List<ProfessorDto> listarProfessores();
}
