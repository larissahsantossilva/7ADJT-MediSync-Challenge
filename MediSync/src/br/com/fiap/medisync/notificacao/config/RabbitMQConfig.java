package br.com.fiap.medisync.notificacao.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nome da fila que o listener vai consumir
    public static final String QUEUE = "consulta.status.queue";

    // Onde as msgs serão publicadas
    public static final String EXCHANGE = "consulta.exchange";

    // Chave de roteamento para a fila correta
    public static final String ROUTING_KEY = "consulta.status";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true); // true significa que a fila é durável, ou seja, persiste mesmo após reinicializações
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE); // Cria um exchange do tipo Topic, que permite roteamento baseado em padrões
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY); // Faz a ligação entre a fila e o exchange usando a chave de roteamento
    }
}
