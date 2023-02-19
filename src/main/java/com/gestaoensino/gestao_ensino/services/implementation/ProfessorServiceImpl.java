package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import com.gestaoensino.gestao_ensino.domain.repository.ProfessorRepository;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import com.gestaoensino.gestao_ensino.utils.CollectionUtils;
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

    private Integer buscaUltimoNumeroInserido() {
        List<Professor> professoresCadastrados = CollectionUtils.getListFromIterable(professorRepository.findAll());
        if (professoresCadastrados.size() < 1) {
            return 1;
        } else {
            Integer maiorNumeroCadastrado = professoresCadastrados.stream()
                    .map(Professor::getId)
                    .max(Long::compare).get();
            return ++maiorNumeroCadastrado;
        }
    }

    @Override
    @Transactional
    public Professor salvarProfessor(Professor professor) {
        professor.setId(buscaUltimoNumeroInserido());
        return professorRepository.save(professor);
    }

    @Override
    @Transactional
    public Professor editarProfessor(Professor professorNovo, String id) {
        Professor professorAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(professorNovo, professorAtual, "id");
        return professorAtual = professorRepository.save(professorAtual);
    }

    @Override
    @Transactional
    public void apagarProfessor(String id) {
        Professor professor = buscarOuFalhar(id);
        professorRepository.delete(professor);
    }

    @Override
    public Professor buscarProfessor(String id) {
        return buscarOuFalhar(id);
    }

    public Professor buscarOuFalhar(String id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("professor.nao.encontrado", id)));

    }

    @Override
    public List<Professor> listarProfessores() {
      return CollectionUtils.getListFromIterable(professorRepository.findAll());
    }
}
