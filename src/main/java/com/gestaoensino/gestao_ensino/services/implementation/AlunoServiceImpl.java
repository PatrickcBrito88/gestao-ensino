package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.domain.exception.AlunoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.repository.AlunoRepository;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    private final ModelMapper modelMapper;

    public AlunoServiceImpl(AlunoRepository alunoRepository, ModelMapper modelMapper) {
        this.alunoRepository = alunoRepository;
        this.modelMapper = modelMapper;
    }

    public Aluno buscarOuFalhar(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

    }

    @Override
    public AlunoDTO salvarAluno(AlunoDTO alunoDto) {
        Aluno aluno = modelMapper.map(alunoDto, Aluno.class);
        return modelMapper.map(alunoRepository.save(aluno), AlunoDTO.class);
    }

    @Override
    public AlunoDTO editarAluno(Aluno alunoNovo, Long id) {
        Aluno alunoAntigo = buscarOuFalhar(id);
        BeanUtils.copyProperties(alunoNovo, alunoAntigo, "id");
        return modelMapper.map(alunoRepository.save(alunoAntigo), AlunoDTO.class);
    }

    @Override
    public void apagarAluno(Long id) {
        Aluno aluno = buscarOuFalhar(id);
        alunoRepository.delete(aluno);
    }

    @Override
    public AlunoDTO buscarAluno(Long id) {
        return modelMapper.map(buscarOuFalhar(id), AlunoDTO.class);
    }

    @Override
    public List<AlunoDTO> listarAlunos() {
        return alunoRepository.findAll().stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());
    }
}
