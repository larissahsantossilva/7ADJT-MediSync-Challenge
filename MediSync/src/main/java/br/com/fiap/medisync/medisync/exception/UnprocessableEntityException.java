package br.com.fiap.medisync.medisync.exception;

public class UnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnprocessableEntityException(String errorMessage){
        super(errorMessage);
    }
}