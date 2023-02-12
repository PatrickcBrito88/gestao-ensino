package com.gestaoensino.gestao_ensino.api.exceptions;


import com.fasterxml.jackson.databind.JsonNode;

public class ServiceFeignException extends RuntimeException {

    private final transient JsonNode data;
    private final int status;

    public ServiceFeignException(int status, String message, JsonNode data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public JsonNode getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
