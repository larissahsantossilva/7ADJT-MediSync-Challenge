package br.com.fiap.medisync.notificacao.errorHandler;

import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.support.GenericMessage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NotificacaoErrorHandlerTest {

    @Test
    void deveExecutarHandleErrorSemLancarExcecao() {
        NotificacaoErrorHandler handler = new NotificacaoErrorHandler();

        Message amqpMessage = new Message("erro".getBytes());
        Channel channel = null;
        GenericMessage<String> springMessage = new GenericMessage<>("mensagem");
        ListenerExecutionFailedException exception = new ListenerExecutionFailedException("falha", new RuntimeException("erro interno"));

        assertDoesNotThrow(() ->
                handler.handleError(amqpMessage, channel, springMessage, exception)
        );
    }
}
