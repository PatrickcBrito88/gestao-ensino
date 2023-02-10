package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.DisciplinaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.domain.exception.DisciplinaNaoEncontradaException;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import com.gestaoensino.gestao_ensino.domain.repository.DisciplinaRepository;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaAssembler disciplinaAssembler;

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository,
                                 ModelMapper modelMapper,
                                 DisciplinaAssembler disciplinaAssembler) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaAssembler = disciplinaAssembler;
    }

    private Disciplina buscarOuFalhar(Long id){
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(id));
    }

    @Override
    @Transactional
    public Disciplina salvarDisciplina(DisciplinaDTO disciplinaDTO) {
        return disciplinaRepository.save(disciplinaAssembler.desmontaDto(disciplinaDTO));
    }

    @Override
    @Transactional
    public Disciplina editarDisciplina(Disciplina disciplinaNova, Long id) {
        Disciplina disciplinaAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(disciplinaNova, disciplinaAtual, "id");
        return disciplinaRepository.save(disciplinaAtual);
    }

    @Override
    @Transactional
    public void apagarDisciplina(Long id) {
        Disciplina disciplina = buscarOuFalhar(id);
        disciplinaRepository.delete(disciplina);
    }

    @Override
    public Disciplina buscarDisciplina(Long id) {
        return buscarOuFalhar(id);
    }

    @Override
    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }
}
