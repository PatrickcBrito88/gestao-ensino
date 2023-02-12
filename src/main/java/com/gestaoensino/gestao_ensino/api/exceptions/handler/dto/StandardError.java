package com.gestaoensino.gestao_ensino.api.exceptions.handler.dto;

public class StandardError {

    private String erro;

    public StandardError() {
    }

    public StandardError(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
