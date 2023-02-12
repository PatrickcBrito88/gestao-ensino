package com.gestaoensino.gestao_ensino.api.exceptions.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.gestaoensino.gestao_ensino.api.exceptions.*;
import com.gestaoensino.gestao_ensino.api.exceptions.handler.dto.EntityErrorResponse;
import com.gestaoensino.gestao_ensino.api.exceptions.handler.dto.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);
    public static final String ERRO_NA_REQUISICAO = "Erro na requisição";

    @ExceptionHandler(ServiceFeignException.class)
    public ResponseEntity<JsonNode> handleServiceFeignException(ServiceFeignException exception) {
        printException(exception);
        return ResponseEntity.status(exception.getStatus()).body(exception.getData());
    }

    @ExceptionHandler(BusinessListException.class)
    public ResponseEntity<EntityErrorResponse> handleBusinessListException(BusinessListException exception) {
        printException(exception);
        EntityErrorResponse response = new EntityErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getErros());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<EntityErrorResponse> handleBusinessException(BusinessException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                ERRO_NA_REQUISICAO, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnauthenticateException.class)
    public ResponseEntity<EntityErrorResponse> handleUnauthenticateException(UnauthenticateException exception) {
        printException(exception);
        List<StandardError> errors = exception
                .getMessages()
                .stream()
                .map(StandardError::new)
                .collect(Collectors.toList());
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                exception.getMessage(), HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                errors);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<EntityErrorResponse> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                ERRO_NA_REQUISICAO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<EntityErrorResponse> handleStorageException(StorageException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                ERRO_NA_REQUISICAO, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

//    @ExceptionHandler(AmazonServiceException.class)
//    public ResponseEntity<EntityErrorResponse> handleAmazonServiceException(AmazonServiceException exception) {
//        printException(exception);
//        List<StandardError> errors = new ArrayList<>();
//        errors.add(new StandardError(exception.getLocalizedMessage()));
//        EntityErrorResponse response = new EntityErrorResponse(
//                "Erro na requisição ao serviço da AWS", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    @ExceptionHandler(DynamoException.class)
    public ResponseEntity<EntityErrorResponse> handleDynamoException(DynamoException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                "Erro na operação do banco de dados.", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<EntityErrorResponse> handleIOException(IOException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                "Erro de I/O", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<EntityErrorResponse> handleReportException(ReportException exception) {
        printException(exception);
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                "Erro na geração do relatório.", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private void printException(Exception exception) {
        LOGGER.error("[EXCEPTION]: {}", exception.getLocalizedMessage());
        exception.printStackTrace();
    }
}
