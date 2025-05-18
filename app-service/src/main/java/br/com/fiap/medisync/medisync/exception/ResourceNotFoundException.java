package br.com.fiap.medisync.medisync.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}