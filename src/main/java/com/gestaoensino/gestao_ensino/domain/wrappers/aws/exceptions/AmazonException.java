package com.gestaoensino.gestao_ensino.domain.wrappers.aws.exceptions;

public class AmazonException extends RuntimeException {

	private static final long serialVersionUID = 2044073089606553511L;

	public AmazonException(String message) {
		super(message);
	}

	public AmazonException(String message, Throwable cause) {
		super(message, cause);
	}
}
