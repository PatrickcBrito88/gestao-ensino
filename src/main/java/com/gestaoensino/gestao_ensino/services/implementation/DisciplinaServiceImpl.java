package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.DisciplinaAssembler;
import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import com.gestaoensino.gestao_ensino.domain.repository.DisciplinaRepository;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.utils.CollectionUtils;
import com.gestaoensino.gestao_ensino.utils.StringUtils;
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

    private Integer buscaUltimoNumeroInserido() {
        List<Disciplina> disciplinasCadastradas = CollectionUtils.getListFromIterable(disciplinaRepository.findAll());
        if (disciplinasCadastradas.size() < 1) {
            return 1;
        } else {
            Integer maiorNumeroCadastrado = disciplinasCadastradas.stream()
                    .map(Disciplina::getId)
                    .max(Long::compare).get();
            return ++maiorNumeroCadastrado;
        }
    }

    private Disciplina buscarOuFalhar(String id){
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("disciplina.nao.encontrada", id)));
    }

    @Override
    public Disciplina salvarDisciplina(Disciplina disciplina) {
        disciplina.setId(buscaUltimoNumeroInserido());
        return disciplinaRepository.save(disciplina);
    }

    @Override
    public Disciplina editarDisciplina(Disciplina disciplinaNova, String id) {
        Disciplina disciplinaAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(disciplinaNova, disciplinaAtual, "id");
        return disciplinaRepository.save(disciplinaAtual);
    }

    @Override
    public void apagarDisciplina(String id) {
        Disciplina disciplina = buscarOuFalhar(id);
        disciplinaRepository.delete(disciplina);
    }

    @Override
    public Disciplina buscarDisciplina(String id) {
        return buscarOuFalhar(id);
    }

    @Override
    public List<Disciplina> listarDisciplinas() {
        return CollectionUtils.getListFromIterable(disciplinaRepository.findAll());
    }
}
