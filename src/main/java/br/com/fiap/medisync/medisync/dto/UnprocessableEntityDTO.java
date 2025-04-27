package br.com.fiap.medisync.medisync.dto;

public record UnprocessableEntityDTO(int statusCode, String errorMessage) {

}