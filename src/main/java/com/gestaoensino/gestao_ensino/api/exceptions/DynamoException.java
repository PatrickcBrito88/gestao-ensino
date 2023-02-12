package com.gestaoensino.gestao_ensino.api.exceptions;

public class DynamoException extends RuntimeException {

	private static final long serialVersionUID = -8054986670265964574L;
	
	public DynamoException(String message) {
        super(message);
    }
    public DynamoException(String message, Throwable cause) {
        super(message, cause);
    }
}
