package com.gestaoensino.gestao_ensino.domain.exception;

public class DisciplinaNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public DisciplinaNaoEncontradaException(String mensagem) {
        super (mensagem);
    }

    public DisciplinaNaoEncontradaException(Long disciplinaId) {
        this(String.format("Não existe disciplina de código %d ", disciplinaId));
    }

}
