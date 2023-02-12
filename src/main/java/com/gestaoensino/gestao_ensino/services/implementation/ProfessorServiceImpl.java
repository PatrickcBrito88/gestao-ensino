package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.Professor;
import com.gestaoensino.gestao_ensino.domain.repository.ProfessorRepository;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import com.gestaoensino.gestao_ensino.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    @Transactional
    public Professor salvarProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    @Transactional
    public Professor editarProfessor(Professor professorNovo, Long id) {
        Professor professorAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(professorNovo, professorAtual, "id");
        return professorAtual = professorRepository.save(professorAtual);
    }

    @Override
    @Transactional
    public void apagarProfessor(Long id) {
        Professor professor = buscarOuFalhar(id);
        professorRepository.delete(professor);
    }

    @Override
    public Professor buscarProfessor(Long id) {
        return buscarOuFalhar(id);
    }

    public Professor buscarOuFalhar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("professor.nao.encontrado", id)));

    }

    @Override
    public List<Professor> listarProfessores() {
      return professorRepository.findAll();
    }
}
