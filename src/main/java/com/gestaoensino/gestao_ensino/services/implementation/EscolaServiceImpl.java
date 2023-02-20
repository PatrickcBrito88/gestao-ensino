package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.exceptions.RecursoNaoEncontradoException;
import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;
import com.gestaoensino.gestao_ensino.domain.repository.redis.EscolaRepository;
import com.gestaoensino.gestao_ensino.services.EscolaService;
import com.gestaoensino.gestao_ensino.utils.CollectionUtils;
import com.gestaoensino.gestao_ensino.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaServiceImpl implements EscolaService {

    private final EscolaRepository escolaRepository;

    public EscolaServiceImpl(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }

    private Integer buscaUltimoNumeroInserido() {
        List<Escola> escolasCadastradas = CollectionUtils.getListFromIterable(escolaRepository.findAll());
        if (escolasCadastradas.size() < 1) {
            return 1;
        } else {
            Integer maiorNumeroCadastrado = escolasCadastradas.stream()
                    .map(Escola::getId)
                    .max(Integer::compare).get();
            return ++maiorNumeroCadastrado;
        }
    }

    private Escola buscarOuFalhar(Integer id){
        return escolaRepository.findById(id.toString())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        StringUtils.getMensagemValidacao("escola.nao.encontrada", id)));
    }

    @Override
    public Escola cadastrarEscola(Escola escola) {
        escola.setId(buscaUltimoNumeroInserido());
        return escolaRepository.save(escola);
    }

    @Override
    public Escola buscarEscola(Integer id) {
        return buscarOuFalhar(id);
    }

    @Override
    public List<Escola> listarEscolas() {
        return CollectionUtils.getListFromIterable(escolaRepository.findAll());
    }

}
