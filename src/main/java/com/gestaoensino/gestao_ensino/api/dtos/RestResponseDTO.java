package com.gestaoensino.gestao_ensino.api.dtos;

import com.gestaoensino.gestao_ensino.api.enums.ResponseStatusEnum;

public class RestResponseDTO<T> {

	private ResponseStatusEnum status;
	private transient T resultado;

	public RestResponseDTO() {
		super();
	}
	
	public RestResponseDTO(final ResponseStatusEnum response, final T dados) {
		super();
		this.status = response;
		this.resultado = dados;
	}

	public ResponseStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ResponseStatusEnum status) {
		this.status = status;
	}

	public T getResultado() {
		return resultado;
	}

	public void setResultado(T resultado) {
		this.resultado = resultado;
	}

	
}
