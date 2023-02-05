package com.gestaoensino.gestao_ensino.domain.exception;

public class ProfessorNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public ProfessorNaoEncontradoException (String mensagem) {
        super (mensagem);
    }

    public ProfessorNaoEncontradoException (Long professorId) {
        this(String.format("Não existe professor de código %d ", professorId));
    }

}
