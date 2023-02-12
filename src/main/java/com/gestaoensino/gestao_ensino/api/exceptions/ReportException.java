package com.gestaoensino.gestao_ensino.api.exceptions;

public class ReportException extends RuntimeException {

	private static final long serialVersionUID = -2350112066856722254L;
	
	public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
