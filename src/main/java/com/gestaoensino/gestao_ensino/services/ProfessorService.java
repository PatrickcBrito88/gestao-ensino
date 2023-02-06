package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.domain.model.Professor;

import java.util.List;

public interface ProfessorService {

    ProfessorDTO salvarProfessor (ProfessorDTO professorDto);
    ProfessorDTO editarProfessor(Professor professor, Long id);
    void apagarProfessor(Long id);
    ProfessorDTO buscarProfessor(Long id);
    List<ProfessorDTO> listarProfessores();
}
