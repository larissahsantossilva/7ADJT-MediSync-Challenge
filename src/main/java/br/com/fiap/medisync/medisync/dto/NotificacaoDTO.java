package br.com.fiap.medisync.medisync.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoDTO {
    private String titulo;
    private String mensagem;
    private String destinatario;
    // Getters e setters
}