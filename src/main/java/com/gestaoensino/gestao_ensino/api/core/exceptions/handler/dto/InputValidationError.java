package com.gestaoensino.gestao_ensino.api.core.exceptions.handler.dto;

public class InputValidationError {

    private String erro;
    private String campo;
    private Object valor;

    public InputValidationError() {
    }

    public InputValidationError(String erro, String campo, Object valor) {
        this.erro = erro;
        this.campo = campo;
        this.valor = valor;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
