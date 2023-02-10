package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.AlunoAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.domain.exception.AlunoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.repository.AlunoRepository;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoAssembler alunoAssembler;

    public AlunoServiceImpl(AlunoRepository alunoRepository, AlunoAssembler alunoAssembler) {
        this.alunoRepository = alunoRepository;
        this.alunoAssembler = alunoAssembler;
    }

    public Aluno buscarOuFalhar(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
    }

    @Override
    public Aluno salvarAluno(AlunoDTO alunoDto) {
        return alunoRepository.save(alunoAssembler.desmontaDto(alunoDto));
    }

    @Override
    public Aluno editarAluno(Aluno alunoNovo, Long id) {
        Aluno alunoAntigo = buscarOuFalhar(id);
        BeanUtils.copyProperties(alunoNovo, alunoAntigo, "id");
        return alunoRepository.save(alunoAntigo);
    }

    @Override
    public void apagarAluno(Long id) {
        Aluno aluno = buscarOuFalhar(id);
        alunoRepository.delete(aluno);
    }

    @Override
    public Aluno buscarAluno(Long id) {
        return buscarOuFalhar(id);
    }

    @Override
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }
}
