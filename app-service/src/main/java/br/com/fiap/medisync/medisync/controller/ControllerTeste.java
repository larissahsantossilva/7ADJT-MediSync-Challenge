package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.NotificacaoEventoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.configuration.RabbitMQConfig.EXCHANGE;
import static br.com.fiap.medisync.medisync.configuration.RabbitMQConfig.ROUTING_KEY;

@RestController
public class ControllerTeste {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public String sendTestMessage(@RequestParam String status) {
        NotificacaoEventoDTO msg = new NotificacaoEventoDTO();
        msg.setConsultaId(UUID.randomUUID());
        msg.setPacienteId(UUID.randomUUID());
        msg.setStatus(status);
        msg.setDataConsulta(LocalDate.now().plusDays(2));
        msg.setMensagem("Lembrete: consulta com status " + status);

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        return "Mensagem enviada com status: " + status;
    }
}
