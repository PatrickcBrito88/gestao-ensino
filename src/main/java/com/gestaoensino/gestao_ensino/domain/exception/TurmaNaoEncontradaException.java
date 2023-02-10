package com.gestaoensino.gestao_ensino.domain.exception;

public class TurmaNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public TurmaNaoEncontradaException(String mensagem) {
        super (mensagem);
    }

    public TurmaNaoEncontradaException(Long turmaId) {
        this(String.format("Não existe turma de código %d ", turmaId));
    }

}
