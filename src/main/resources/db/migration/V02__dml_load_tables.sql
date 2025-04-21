-- Usa o schema medisync
SET search_path TO medisync;

-- Usuários
INSERT INTO usuario (id, nome, email, login, senha, cpf, data_nascimento, telefone, criado_em, ultima_alteracao)
VALUES
    (uuid_generate_v4(), 'Dr. João Silva', 'joao.silva@medisync.com', 'joaosilva', 'senha123', '12345678901', '1980-04-01', '11999999999', CURRENT_DATE, CURRENT_DATE),
    (uuid_generate_v4(), 'Enf. Maria Souza', 'maria.souza@medisync.com', 'mariasouza', 'senha123', '12345678902', '1990-07-12', '11888888888', CURRENT_DATE, CURRENT_DATE),
    (uuid_generate_v4(), 'Carlos Lima', 'carlos.lima@medisync.com', 'carloslima', 'senha123', '12345678903', '2000-01-15', '11777777777', CURRENT_DATE, CURRENT_DATE);

-- Médico
INSERT INTO medico (id, id_usuario, especialidade, crm, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'joaosilva'),
           'Cardiologia',
           'CRM-SP-12345',
           CURRENT_DATE, CURRENT_DATE
       );

-- Enfermeiro
INSERT INTO enfermeiro (id, id_usuario, especialidade, coren, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'mariasouza'),
           'Medicina Intensiva',
           'COREN-SP-67890',
           CURRENT_DATE, CURRENT_DATE
       );

-- Paciente
INSERT INTO paciente (id, id_usuario, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'carloslima'),
           CURRENT_DATE, CURRENT_DATE
       );

-- Unidade de Saúde
INSERT INTO unidade_saude (id, nome, endereco, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           'Posto Central',
           'Rua das Flores, 123',
           CURRENT_DATE, CURRENT_DATE
       );

-- Consulta
INSERT INTO consulta (
    id, id_paciente, id_medico, id_enfermeiro, id_unidade_saude,
    observacao, data_consulta, status, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT p.id FROM paciente p JOIN usuario u ON p.id_usuario = u.id WHERE u.login = 'carloslima'),
           (SELECT m.id FROM medico m JOIN usuario u ON m.id_usuario = u.id WHERE u.login = 'joaosilva'),
           (SELECT e.id FROM enfermeiro e JOIN usuario u ON e.id_usuario = u.id WHERE u.login = 'mariasouza'),
           (SELECT id FROM unidade_saude WHERE nome = 'Posto Central'),
           'Consulta de rotina',
           CURRENT_DATE + INTERVAL '1 day',
           'Agendada',
           CURRENT_DATE, CURRENT_DATE
       );

-- Notificação
INSERT INTO notificacao (
    id, id_paciente, id_consulta, mensagem, data_envio, status, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT p.id FROM paciente p JOIN usuario u ON p.id_usuario = u.id WHERE u.login = 'carloslima'),
           (SELECT c.id FROM consulta c
                                 JOIN paciente p ON c.id_paciente = p.id
                                 JOIN usuario u ON p.id_usuario = u.id
            WHERE u.login = 'carloslima'),
           'Sua consulta está agendada para amanhã.',
           CURRENT_DATE,
           'Enviado',
           CURRENT_DATE,
           CURRENT_DATE
       );
