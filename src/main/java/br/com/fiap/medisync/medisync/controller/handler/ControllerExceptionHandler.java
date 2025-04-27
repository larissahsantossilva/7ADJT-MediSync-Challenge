package br.com.fiap.medisync.medisync.controller.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.fiap.medisync.medisync.dto.ResourceNotFoundDTO;
import br.com.fiap.medisync.medisync.dto.ValidationErrorDTO;
import br.com.fiap.medisync.medisync.dto.IllegalArgumentDTO;
import br.com.fiap.medisync.medisync.dto.UnprocessableEntityDTO;
import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDTO> handlerResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("ResourceNotFoundException ", e);
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ResourceNotFoundDTO(e.getMessage(), status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        for (var error: e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDTO(errors, status.value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResourceNotFoundDTO> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex.getName().equals("id") && ex.getRequiredType().equals(UUID.class)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResourceNotFoundDTO("ID não encontrado", 404));
        }
        return ResponseEntity.internalServerError().body(new ResourceNotFoundDTO("Erro interno do servidor", 500));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<UnprocessableEntityDTO> handlerUnprocessableEntityException(UnprocessableEntityException e) {
        logger.error("UnprocessableEntityException ", e);
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status.value()).body(new UnprocessableEntityDTO(status.value(), e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentDTO> handlerIllegalArgumentException(IllegalArgumentException e) {
        logger.error("IllegalArgumentException ", e);
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new IllegalArgumentDTO(status.value(), e.getMessage()));
    }
}