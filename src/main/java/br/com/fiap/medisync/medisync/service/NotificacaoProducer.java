package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.configuration.RabbitMQConfig;
import br.com.fiap.medisync.medisync.dto.NotificacaoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(NotificacaoDTO notificacao) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY,
            notificacao
        );
    }
}
