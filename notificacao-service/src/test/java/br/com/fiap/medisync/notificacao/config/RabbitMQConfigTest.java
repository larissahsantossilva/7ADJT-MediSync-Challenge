package br.com.fiap.medisync.notificacao.config;

import br.com.fiap.medisync.notificacao.configuration.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RabbitMQConfigTest {

    private final br.com.fiap.medisync.notificacao.configuration.RabbitMQConfig config = new br.com.fiap.medisync.notificacao.configuration.RabbitMQConfig();

    @Test
    void deveCriarFilaDuravelComNomeCorreto() {
        Queue queue = config.queue();
        assertNotNull(queue);
        assertEquals("consulta.status.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void deveCriarExchangeDoTipoTopicComNomeCorreto() {
        TopicExchange exchange = config.exchange();
        assertNotNull(exchange);
        assertEquals("consulta.exchange", exchange.getName());
    }

    @Test
    void deveCriarBindingCorretoEntreFilaEExchange() {
        Queue queue = config.queue();
        TopicExchange exchange = config.exchange();

        Binding binding = config.binding(queue, exchange);

        assertNotNull(binding);
        assertEquals("consulta.status", binding.getRoutingKey());
        assertEquals(Binding.DestinationType.QUEUE, binding.getDestinationType());
    }

    @Test
    void deveCriarJacksonMessageConverter() {
        Jackson2JsonMessageConverter converter = config.messageConverter();
        assertNotNull(converter);
    }

    @Test
    void deveCriarListenerFactorySemExcecao() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Jackson2JsonMessageConverter converter = config.messageConverter();

        SimpleRabbitListenerContainerFactory factory =
                config.rabbitListenerContainerFactory(connectionFactory, converter);

        assertNotNull(factory);
    }

}