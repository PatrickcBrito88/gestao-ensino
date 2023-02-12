package com.gestaoensino.gestao_ensino.api.exceptions;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -2428408143201405065L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}