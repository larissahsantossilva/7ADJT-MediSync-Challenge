# 7ADJT-MediSync-Challenge
Este projeto foi desenvolvido como parte do Tech Challenge, com o objetivo de facilitar o agendamento de consultas, o gerenciamento de prontuários e a comunicação entre pacientes e profissionais da saúde, garantindo segurança, escalabilidade e uma experiência eficiente para todos os perfis de usuário


### Módulo de Comunicação Assíncrona com RabbitMQ

Este módulo é responsável por processar mensagens enviadas quando uma consulta é criada ou editada. Ele roda como uma aplicação separada, dentro do mesmo repositório.

#### Funcionalidade
- Escuta a fila `consulta.status.queue`
- Recebe mensagens com os dados da consulta e status (`AGENDADO`, `REMARCADO`, `CANCELADO`)
- Persiste notificações na tabela `notificacao` via Spring Data JPA

#### Teste rápido
1. Acesse o painel RabbitMQ: http://localhost:15672 (user: guest / senha: guest)
2. Vá em **Queues > consulta.status.queue > Publish message**
3. Preencha com JSON de exemplo e envie
4. Verifique se a notificação foi salva no banco

#### Tecnologias
- Spring Boot
- Spring AMQP (RabbitMQ)
- PostgreSQL
- Flyway
