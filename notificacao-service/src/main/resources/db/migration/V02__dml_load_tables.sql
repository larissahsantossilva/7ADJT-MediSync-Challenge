SET search_path TO medisync;

-- Insere uma notificação de teste vinculada a paciente e consulta já existentes

INSERT INTO notificacao (
    id,
    id_paciente,
    id_consulta,
    mensagem,
    data_envio,
    status,
    criado_em,
    ultima_alteracao
)
VALUES (
    uuid_generate_v4(),
    -- id_paciente: usa paciente com login 'carloslima'
    (SELECT p.id FROM paciente p
     JOIN usuario u ON u.id = p.id_usuario
     WHERE u.login = 'carloslima'),

    -- id_consulta associada a esse paciente
    (SELECT c.id FROM consulta c
     JOIN paciente p ON c.id_paciente = p.id
     JOIN usuario u ON p.id_usuario = u.id
     WHERE u.login = 'carloslima'
     LIMIT 1),

    'Sua consulta está AGENDADA para amanhã às 10h.',
    CURRENT_TIMESTAMP,
    'AGENDADO',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
