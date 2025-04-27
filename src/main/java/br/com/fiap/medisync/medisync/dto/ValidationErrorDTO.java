package br.com.fiap.medisync.medisync.dto;

import java.util.List;

public record ValidationErrorDTO(List<String> errors, int status) {

}