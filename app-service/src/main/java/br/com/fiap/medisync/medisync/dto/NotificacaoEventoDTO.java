package br.com.fiap.medisync.medisync.dto;

import br.com.fiap.medisync.medisync.model.ConsultaStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoEventoDTO {
    private UUID consultaId;
    private UUID pacienteId;
    private ConsultaStatus status;
    private LocalDateTime dataConsulta;
    private String mensagem;
}