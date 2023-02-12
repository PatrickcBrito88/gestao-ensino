package com.gestaoensino.gestao_ensino.api.exceptions.handler;

import com.gestaoensino.gestao_ensino.api.exceptions.handler.dto.InputErrorResponse;
import com.gestaoensino.gestao_ensino.api.exceptions.handler.dto.InputValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceInputValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceInputValidationExceptionHandler.class);

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        LOGGER.error("[EXCEPTION]: {}", ex.getLocalizedMessage());
        ex.printStackTrace();
        List<InputValidationError> errors = getErrors(ex);
        InputErrorResponse errorResponse = getErrorResponse(status, errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    private InputErrorResponse getErrorResponse(HttpStatus status,
                                                List<InputValidationError> errors) {
        return new InputErrorResponse("Requisição inválida.", status.value(),
                status.getReasonPhrase(), errors);
    }

    private List<InputValidationError> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream().map(
                error -> new InputValidationError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
