SET search_path TO medisync;

-- Populando tabela de especialidade
INSERT INTO especialidade (descricao, criado_em, ultima_alteracao) VALUES
                                                                       ('Acupuntura', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Alergia e Imunologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Anatomia Patológica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Anestesiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Angiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cancerologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cardiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Cardiovascular', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia da Mão', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia de Cabeça e Pescoço', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia do Aparelho Digestivo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Geral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Pediátrica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Plástica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Torácica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Cirurgia Vascular', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Clínica Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Coloproctologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Dermatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Endocrinologia e Metabologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Endoscopia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Gastroenterologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Genética Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Geriatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Ginecologia e Obstetrícia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Hematologia e Hemoterapia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Homeopatia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Infectologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Mastologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina de Emergência', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina de Família e Comunidade', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina do Trabalho', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina do Tráfego', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Esportiva', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Física e Reabilitação', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Intensiva', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Legal e Perícia Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Nuclear', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Medicina Preventiva e Social', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Nefrologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Neurocirurgia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Neurologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Nutrologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Oftalmologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Ortopedia e Traumatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Otorrinolaringologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Patologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Patologia Clínica/Medicina Laboratorial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Pediatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Pneumologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Psiquiatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Radiologia e Diagnóstico por Imagem', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Radioterapia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Reumatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                       ('Urologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserindo usuários
INSERT INTO usuario (id, nome, email, login, senha, cpf, data_nascimento, telefone, criado_em, ultima_alteracao)
VALUES
    (uuid_generate_v4(), 'Dr. João Silva', 'joao.silva@medisync.com', 'joaosilva', 'senha123', '12345678901', '1980-04-01', '11999999999', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), 'Enf. Maria Souza', 'maria.souza@medisync.com', 'mariasouza', 'senha123', '12345678902', '1990-07-12', '11888888888', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), 'Carlos Lima', 'carlos.lima@medisync.com', 'carloslima', 'senha123', '12345678903', '2000-01-15', '11777777777', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Médico
INSERT INTO medico (id, id_usuario, id_especialidade, crm, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'joaosilva'),
           (SELECT id FROM especialidade WHERE descricao = 'Cardiologia'),
           'CRM-SP-12345',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Enfermeiro
INSERT INTO enfermeiro (id, id_usuario, id_especialidade, coren, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'mariasouza'),
           (SELECT id FROM especialidade WHERE descricao = 'Medicina Intensiva'),
           'COREN-SP-67890',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Paciente
INSERT INTO paciente (id, id_usuario, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           (SELECT id FROM usuario WHERE login = 'carloslima'),
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Unidade de saúde
INSERT INTO unidade_saude (id, nome, endereco, criado_em, ultima_alteracao)
VALUES (
           uuid_generate_v4(),
           'Posto Central',
           'Rua das Flores, 123',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
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
           'Consulta de rotina com exame clínico',
           CURRENT_TIMESTAMP + INTERVAL '1 day',
           'AGENDADA',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
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
           'Sua consulta está agendada para amanhã às 10h.',
           CURRENT_TIMESTAMP,
           'Enviado',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );
