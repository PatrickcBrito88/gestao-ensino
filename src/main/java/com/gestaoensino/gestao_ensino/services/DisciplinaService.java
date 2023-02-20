package com.gestaoensino.gestao_ensino.services;

import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;

import java.util.List;

public interface DisciplinaService {

    Disciplina salvarDisciplina (Disciplina disciplina);
    Disciplina editarDisciplina(Disciplina disciplina, Integer id);
    void apagarDisciplina(Integer id);
    Disciplina buscarDisciplina(Integer id);
    List<Disciplina> listarDisciplinas();

}
