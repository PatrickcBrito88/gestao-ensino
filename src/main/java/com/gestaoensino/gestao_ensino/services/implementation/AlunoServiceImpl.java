package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.AlunoAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import com.gestaoensino.gestao_ensino.domain.repository.AlunoRepository;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.utils.CollectionUtils;
import com.gestaoensino.gestao_ensino.utils.StringUtils;
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

    public Aluno buscarOuFalhar(String id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("aluno.nao.encontrado", id)));
    }

    @Override
    public Aluno salvarAluno(AlunoDTO alunoDto) {
        return alunoRepository.save(alunoAssembler.desmontaDto(alunoDto));
    }

    @Override
    public Aluno editarAluno(Aluno alunoNovo, String id) {
        Aluno alunoAntigo = buscarOuFalhar(id);
        BeanUtils.copyProperties(alunoNovo, alunoAntigo, "id");
        return alunoRepository.save(alunoAntigo);
    }

    @Override
    public void apagarAluno(String id) {
        Aluno aluno = buscarOuFalhar(id);
        alunoRepository.delete(aluno);
    }

    @Override
    public Aluno buscarAluno(String id) {
        return buscarOuFalhar(id);
    }

    @Override
    public List<Aluno> listarAlunos() {
        return CollectionUtils.getListFromIterable(alunoRepository.findAll());
    }
}
