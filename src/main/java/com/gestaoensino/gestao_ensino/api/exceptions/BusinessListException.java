package com.gestaoensino.gestao_ensino.api.exceptions;

import com.gestaoensino.gestao_ensino.api.exceptions.handler.dto.StandardError;

import java.util.List;

public class BusinessListException extends RuntimeException {

    private final transient List<StandardError> erros;

    public BusinessListException(String message, List<StandardError> erros) {
        super(message);
        this.erros = erros;
    }

    public BusinessListException(String message, List<StandardError> erros, Throwable cause) {
        super(message, cause);
        this.erros = erros;
    }

    public List<StandardError> getErros() {
        return erros;
    }
}
