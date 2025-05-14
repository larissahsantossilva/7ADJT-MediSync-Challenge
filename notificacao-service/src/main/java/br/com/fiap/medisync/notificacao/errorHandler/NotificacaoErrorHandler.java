package br.com.fiap.medisync.notificacao.errorHandler;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("notificacaoErrorHandler")
public class NotificacaoErrorHandler implements RabbitListenerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoErrorHandler.class);

    @Override
    public Object handleError(org.springframework.amqp.core.Message amqpMessage,
                              Channel channel,
                              Message<?> message,
                              ListenerExecutionFailedException exception) throws Exception {

        logger.error("Erro ao processar mensagem da fila '{}': {}",
                amqpMessage.getMessageProperties().getConsumerQueue(),
                exception.getMessage(), exception);

        logger.debug("Payload recebido (raw): {}", new String(amqpMessage.getBody()));

        return null;
    }
}
