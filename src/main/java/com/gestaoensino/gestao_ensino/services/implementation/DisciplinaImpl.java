package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.domain.exception.DisciplinaNaoEncontradaException;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import com.gestaoensino.gestao_ensino.domain.repository.DisciplinaRepository;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinaImpl  implements DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    public DisciplinaImpl(DisciplinaRepository disciplinaRepository, ModelMapper modelMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.modelMapper = modelMapper;
    }

    private Disciplina buscarOuFalhar(Long id){
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(id));
    }

    @Override
    public DisciplinaDTO salvarDisciplina(DisciplinaDTO disciplinaDTO) {
        return modelMapper.map(
                disciplinaRepository.save(modelMapper.map(disciplinaDTO, Disciplina.class)),
                DisciplinaDTO.class);
    }

    @Override
    public DisciplinaDTO editarDisciplina(Disciplina disciplinaNova, Long id) {
        Disciplina disciplinaAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(disciplinaNova, disciplinaAtual, "id");
        return modelMapper.map(disciplinaRepository.save(disciplinaAtual), DisciplinaDTO.class);
    }

    @Override
    public void apagarDisciplina(Long id) {
        Disciplina disciplina = buscarOuFalhar(id);
        disciplinaRepository.delete(disciplina);
    }

    @Override
    public DisciplinaDTO buscarDisciplina(Long id) {
        return modelMapper.map(buscarDisciplina(id), DisciplinaDTO.class);
    }

    @Override
    public List<DisciplinaDTO> listarDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(disciplina -> modelMapper.map(disciplina, DisciplinaDTO.class))
                .collect(Collectors.toList());
    }
}
