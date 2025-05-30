package br.com.fiap.medisync.notificacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacaoEventoDTO {
    private UUID consultaId;
    private UUID pacienteId;
    private String status;
    private LocalDateTime dataConsulta;
    private String mensagem;
}
