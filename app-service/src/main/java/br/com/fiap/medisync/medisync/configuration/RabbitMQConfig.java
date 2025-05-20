package br.com.fiap.medisync.medisync.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "consulta.status.queue";

    public static final String EXCHANGE = "consulta.exchange";

    public static final String ROUTING_KEY = "consulta.status";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

}
