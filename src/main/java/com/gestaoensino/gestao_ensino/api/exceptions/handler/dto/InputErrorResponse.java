package com.gestaoensino.gestao_ensino.api.exceptions.handler.dto;

import java.util.List;

public class InputErrorResponse extends AbstractErrorResponse {


    private List<InputValidationError> erros;

    public InputErrorResponse() {

    }

    public InputErrorResponse(String mensagem, int codigo, String status,
                              List<InputValidationError> erros) {
        super(mensagem, codigo, status);
        this.erros = erros;
    }

    public List<InputValidationError> getErros() {
        return erros;
    }

    public void setErros(List<InputValidationError> erros) {
        this.erros = erros;
    }
}
