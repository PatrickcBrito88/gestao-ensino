package com.gestaoensino.gestao_ensino.api.resouces.modelo;

import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.enums.ResponseStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GestaoEnsinoResource {

    public GestaoEnsinoResource() {
    }

    private <T> ResponseEntity<RestResponseDTO<T>> retornarResponse(final T response) {
        RestResponseDTO<T> restResponse = new RestResponseDTO();
        if (response != null && (!(response instanceof List) || !((List) response).isEmpty())) {
            restResponse.setStatus(ResponseStatusEnum.OK);
        } else {
            restResponse.setStatus(ResponseStatusEnum.SEM_RESULTADOS);
        }

        restResponse.setResultado(response);
        return ResponseEntity.status(HttpStatus.OK).body(restResponse);
    }

    public <T> ResponseEntity<RestResponseDTO<T>> retornarSucesso(final T response) {
        return this.retornarResponse(response);
    }
}
