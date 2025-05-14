package br.com.fiap.medisync.notificacao.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ConsultaStatusMessageDTO {

    private UUID idConsulta;
    private UUID idPaciente;
    private String status;
    private LocalDate dataConsulta;
    private String mensagem;

    public UUID getIdConsulta() { return idConsulta; }
    public void setIdConsulta(UUID idConsulta) { this.idConsulta = idConsulta; }

    public UUID getIdPaciente() { return idPaciente; }
    public void setIdPaciente(UUID idPaciente) { this.idPaciente = idPaciente; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataConsulta() { return dataConsulta; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
