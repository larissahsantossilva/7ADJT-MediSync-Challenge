package br.com.fiap.medisync.medisync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacaoEventoDTO {
    private UUID consultaId;
    private UUID pacienteId;
    private String status;
    private LocalDate dataConsulta;
    private String mensagem;
}