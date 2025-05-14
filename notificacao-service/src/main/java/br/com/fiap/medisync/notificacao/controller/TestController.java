package br.com.fiap.medisync.notificacao.controller;

import br.com.fiap.medisync.notificacao.dto.ConsultaStatusMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.fiap.medisync.notificacao.config.RabbitMQConfig.EXCHANGE;
import static br.com.fiap.medisync.notificacao.config.RabbitMQConfig.ROUTING_KEY;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public String sendTestMessage(@RequestParam String status) {
        ConsultaStatusMessageDTO msg = new ConsultaStatusMessageDTO();
        msg.setIdConsulta(UUID.randomUUID());
        msg.setIdPaciente(UUID.randomUUID());
        msg.setStatus(status);
        msg.setDataConsulta(LocalDate.now().plusDays(2));
        msg.setMensagem("Lembrete: consulta com status " + status);

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        return "Mensagem enviada com status: " + status;
    }
}
