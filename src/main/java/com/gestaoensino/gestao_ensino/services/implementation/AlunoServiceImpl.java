package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.AlunoAssembler;
import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import com.gestaoensino.gestao_ensino.domain.repository.redis.AlunoRepository;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.utils.CollectionUtils;
import com.gestaoensino.gestao_ensino.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository, AlunoAssembler alunoAssembler) {
        this.alunoRepository = alunoRepository;
    }

    private Integer buscaUltimoNumeroInserido() {
        List<Aluno> alunosCadastrados = CollectionUtils.getListFromIterable(alunoRepository.findAll());
        if (alunosCadastrados.size() < 1) {
            return 1;
        } else {
            Integer maiorNumeroCadastrado = alunosCadastrados.stream()
                    .map(Aluno::getId)
                    .max(Long::compare).get();
            return ++maiorNumeroCadastrado;
        }
    }

    public Aluno buscarOuFalhar(String id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("aluno.nao.encontrado", id)));
    }

    @Override
    public Aluno salvarAluno(Aluno aluno) {
        aluno.setId(buscaUltimoNumeroInserido());
        return alunoRepository.save(aluno);
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
