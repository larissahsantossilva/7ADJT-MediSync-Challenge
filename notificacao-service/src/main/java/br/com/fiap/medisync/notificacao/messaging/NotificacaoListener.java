package br.com.fiap.medisync.notificacao.messaging;

import br.com.fiap.medisync.notificacao.configuration.RabbitMQConfig;
import br.com.fiap.medisync.notificacao.dto.NotificacaoEventoDTO;
import br.com.fiap.medisync.notificacao.model.Notificacao;
import br.com.fiap.medisync.notificacao.repository.NotificacaoRepository;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificacaoListener {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE,  errorHandler = "notificacaoErrorHandler")
    public void receberMensagem(@Payload @Valid NotificacaoEventoDTO mensagem) {

        Notificacao notificacao = new Notificacao();
        notificacao.setConsultaId(mensagem.getConsultaId());
        notificacao.setPacienteId(mensagem.getPacienteId());
        notificacao.setMensagem(mensagem.getMensagem());
        notificacao.setDataEnvio(mensagem.getDataConsulta());
        notificacao.setCriadoEm(LocalDateTime.now());
        notificacao.setUltimaAlteracao(LocalDateTime.now());

        notificacaoRepository.save(notificacao);
    }
}
