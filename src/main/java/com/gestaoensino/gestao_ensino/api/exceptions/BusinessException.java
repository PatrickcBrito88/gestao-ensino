package com.gestaoensino.gestao_ensino.api.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1736608613900288593L;
	
	public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
