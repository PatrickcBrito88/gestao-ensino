package com.gestaoensino.gestao_ensino.api.core.exceptions;

import java.util.ArrayList;
import java.util.List;

public class UnauthenticateException extends RuntimeException {
    private final List<String> messages = new ArrayList<>();


    public UnauthenticateException(String message) {
        super(message);
    }

    public UnauthenticateException(String message, Throwable cause) {
        super(message, cause);
    }


    public List<String> getMessages() {
        return messages;
    }

    public UnauthenticateException appendMessage(String message) {
        messages.add(message);
        return this;
    }
}
