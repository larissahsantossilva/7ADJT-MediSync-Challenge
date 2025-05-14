package br.com.fiap.medisync.notificacao.messaging;

import br.com.fiap.medisync.notificacao.config.RabbitMQConfig;
import br.com.fiap.medisync.notificacao.dto.ConsultaStatusMessageDTO;
import br.com.fiap.medisync.medisync.model.Notificacao;
import br.com.fiap.medisync.notificacao.repository.NotificacaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificacaoListener {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    /*
     * Método que escuta as mensagens na fila configurada no RabbitMQ.
     * Quando uma mensagem é recebida, ela é convertida para o objeto ConsultaStatusMessageDTO
     * e salva no banco de dados como uma nova Notificacao.
     *
     * @param mensagem A mensagem recebida do RabbitMQ, convertida para o objeto ConsultaStatusMessageDTO.
     *
     * Arquitetura event-driven: O microserviço Notificacao é acionado por eventos (mensagens) que chegam através do RabbitMQ.
    * */
    @RabbitListener(queues = RabbitMQConfig.QUEUE) // Ouve as mensagens na fila configurada
    public void receberMensagem(ConsultaStatusMessageDTO mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setIdConsulta(mensagem.getIdConsulta());
        notificacao.setIdPaciente(mensagem.getIdPaciente());
        notificacao.setMensagem(mensagem.getMensagem());
        notificacao.setDataEnvio(mensagem.getDataConsulta());
        notificacao.setCriadoEm(LocalDateTime.now());
        notificacao.setUltimaAlteracao(LocalDateTime.now());

        notificacaoRepository.save(notificacao);
    }
}
