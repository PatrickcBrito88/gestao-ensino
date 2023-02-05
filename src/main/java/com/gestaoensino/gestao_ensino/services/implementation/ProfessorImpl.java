package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDto;
import com.gestaoensino.gestao_ensino.domain.exception.ProfessorNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.Professor;
import com.gestaoensino.gestao_ensino.domain.repository.ProfessorRepository;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorImpl implements ProfessorService {

    private ProfessorRepository professorRepository;
    private ModelMapper modelMapper;

    public ProfessorImpl(ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessorDto salvarProfessor(ProfessorDto professorDto) {
        Professor professor = modelMapper.map(professorDto, Professor.class);
        return modelMapper.map(
                professorRepository.save(professor), ProfessorDto.class);
    }

    @Override
    public ProfessorDto editarProfessor(Professor professorNovo, Long id) {
        Professor professorAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(professorNovo, professorAtual, "id");
        professorAtual = professorRepository.save(professorAtual);
        return modelMapper.map(professorAtual, ProfessorDto.class);
    }

    @Override
    public void apagarProfessor(Long id) {
        Professor professor = buscarOuFalhar(id);
        professorRepository.delete(professor);
    }

    @Override
    public ProfessorDto buscarProfessor(Long id) {
        Professor professor = buscarOuFalhar(id);
        return modelMapper.map(professor, ProfessorDto.class);
    }

    public Professor buscarOuFalhar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNaoEncontradoException(id));

    }

    @Override
    public List<ProfessorDto> listarProfessores() {
        return professorRepository.findAll().stream()
                .map(professor -> modelMapper.map(professor, ProfessorDto.class))
                .collect(Collectors.toList());
    }
}
