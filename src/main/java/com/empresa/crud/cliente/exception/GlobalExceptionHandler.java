package com.empresa.crud.cliente.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        String mensagem = ex.getMessage();
    if (mensagem != null){
        if (mensagem.contains("já cadastrado") || mensagem.contains("obrigatório")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
        }
        if (mensagem.contains("não encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagem);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
    }
}
