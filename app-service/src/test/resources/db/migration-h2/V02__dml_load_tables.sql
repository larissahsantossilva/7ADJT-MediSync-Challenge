SET search_path TO medisync;

-- Populando tabela de especialidade
INSERT INTO especialidade (id, descricao, criado_em, ultima_alteracao) VALUES
                                                                           ('2f67aa4e-1fc1-43de-9229-9f5f5d06c3cf', 'Acupuntura', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('09f4ce10-042c-4e69-8b46-c7b1103ed733', 'Alergia e Imunologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('0b376db1-d73c-47e7-a705-d0ef93d74247', 'Anatomia Patológica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('1c284ce4-e387-4983-9e95-5d88ec6404a6', 'Anestesiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('9b2b89a7-4766-4725-99ad-92e318dbb172', 'Angiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('7c2c57d9-6431-453a-80ff-381bc165f5f0', 'Cancerologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('b6187b67-e4fd-4ebf-9ad4-65db78a67b33', 'Cardiologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('95e88250-c7e5-4cf5-bf69-515ca3cd8d0e', 'Cirurgia Cardiovascular', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('fb9ce4b3-c63c-45ef-b2bb-f0e112a648bb', 'Cirurgia da Mão', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('02dd5c66-f9d1-4212-b29f-42de142e7ce4', 'Cirurgia de Cabeça e Pescoço', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('cdcadb6a-e07b-4a00-8f73-3e315f04c6e0', 'Cirurgia do Aparelho Digestivo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('b8cc6e4a-5c9d-4b46-b08d-3bb5eb5881c1', 'Cirurgia Geral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('8b8f0c29-f8ff-4e86-ae9e-1efb6db35116', 'Cirurgia Pediátrica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('5f96b7d7-bd01-4e06-ae1a-cd08737649c5', 'Cirurgia Plástica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('f9d5aa2d-07f6-4631-99e2-85004a836e07', 'Cirurgia Torácica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('6f8e3242-f858-49dc-91df-33c15ef87ff4', 'Cirurgia Vascular', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('e8718a62-d946-4e6a-a71c-b9dfe5b2e34e', 'Clínica Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('239482fc-8bc4-43f1-8776-50e849c97338', 'Coloproctologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('636c5d90-e014-4554-91f9-6c2ff076d674', 'Dermatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('2f408746-cc4e-4ba1-924c-7e1f2b4a6a65', 'Endocrinologia e Metabologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('7021c69c-fbe1-4724-8782-99f7334b2b49', 'Endoscopia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('3f6db8c1-48d7-4e23-b84b-9b2173dbb95c', 'Gastroenterologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('d2e7ae4e-1192-45b7-9949-42f626cbd1f5', 'Genética Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('e5082f47-5fc6-4c80-a657-800bff44a201', 'Geriatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('3a01fcfa-5d84-4b33-9b85-958690cb2a97', 'Ginecologia e Obstetrícia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('f4816233-0587-49ff-a94b-6b169e48fc97', 'Hematologia e Hemoterapia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('3608f59a-e80e-4f30-91d3-08bb53d47e18', 'Homeopatia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('ee2a6f4c-5f4b-4af9-a8a3-84fcb8a92462', 'Infectologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('b2beee39-d43e-40a2-964e-24ddc06c199e', 'Mastologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('57494be7-eaae-4a5a-9333-7c2722532583', 'Medicina de Emergência', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('1e5b49f1-bb64-4324-92b3-fd925719e6cf', 'Medicina de Família e Comunidade', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('00bb2653-e2ab-491c-b388-5a6b2f2f9c73', 'Medicina do Trabalho', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('a69e8030-8d83-46d0-a6bc-265b57447d18', 'Medicina do Tráfego', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('014539a0-678f-49e2-87b6-84f22782a9c4', 'Medicina Esportiva', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('96d9b8db-f1fa-4e8b-b2d1-c8a69dbda342', 'Medicina Física e Reabilitação', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('5790a77c-9914-47e6-8a38-8a7776e7a0da', 'Medicina Intensiva', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('157bd7c5-e0bc-4b87-b31c-9ac8b9a899b1', 'Medicina Legal e Perícia Médica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('ce42e8c4-498b-4346-bc3c-4dcfb49b3bff', 'Medicina Nuclear', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('cc5958f0-42e9-496f-b8d1-d24fc3f9733e', 'Medicina Preventiva e Social', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('6ed8e9e0-3a4b-49d7-a345-f0b9cbfe2037', 'Nefrologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('a6e58c2c-44fd-421e-8771-d8a465d1d278', 'Neurocirurgia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('cd8cb76e-e3f1-43fc-afe6-7d27f7a169de', 'Neurologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('144a7a1d-9e17-4cf6-aaa1-4cb3a5151bb0', 'Nutrologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('b8e27a5d-46b0-4970-a9df-1cf70bc98e9b', 'Oftalmologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('a801c622-f84a-4223-9a57-d6adcfaf2712', 'Ortopedia e Traumatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('dba5ecfb-3cf1-4a11-95fa-5747ad7284e0', 'Otorrinolaringologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('0c409ce4-6f1d-433e-8e59-5aa9f6c20d6e', 'Patologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('e1d77b34-91cb-4bc0-8430-f87229e5970d', 'Patologia Clínica/Medicina Laboratorial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('538eb7cd-45cb-4a3f-bb4d-36756a2ee12c', 'Pediatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('1a26cd4b-f5a3-4f28-84ab-61a3c0cfbfe5', 'Pneumologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('e3271c4a-7ba4-48e0-a158-f2388e15dff9', 'Psiquiatria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('774d338f-4e5c-4a1e-9560-0bdb1b4d7be2', 'Radiologia e Diagnóstico por Imagem', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('26cb0cd2-947a-40c6-a1ae-93451a9ff82e', 'Radioterapia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('0bdbfce0-81f5-41a0-8b7b-8b81f6178491', 'Reumatologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('72c0f0e7-1993-4be0-bb67-e8fa54ed70e6', 'Urologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Inserindo usuários
INSERT INTO usuario (id, nome, email, login, senha, cpf, data_nascimento, telefone, criado_em, ultima_alteracao)
VALUES
    ('abafd554-f16b-4de2-828a-a9612ed24fc5', 'Dr. João Silva', 'joao.silva@medisync.com', 'joaosilva', 'senha123', '12345678901', '1980-04-01', '11999999999', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ea871b92-ab82-4828-afd9-1b7c5887b0ff', 'Enf. Maria Souza', 'maria.souza@medisync.com', 'mariasouza', 'senha123', '12345678902', '1990-07-12', '11888888888', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('2f69a22c-d2ad-4480-8d99-2b6ea6f35030', 'Carlos Lima', 'carlos.lima@medisync.com', 'carloslima', 'senha123', '12345678903', '2000-01-15', '11777777777', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Médico
INSERT INTO medico (id, id_usuario, id_especialidade, crm, criado_em, ultima_alteracao)
VALUES (
           'f8a29b95-9c04-4d51-92dd-6d055d8c18fc',
           (SELECT id FROM usuario WHERE login = 'joaosilva'),
           (SELECT id FROM especialidade WHERE descricao = 'Cardiologia'),
           'CRM-SP-12345',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Enfermeiro
INSERT INTO enfermeiro (id, id_usuario, id_especialidade, coren, criado_em, ultima_alteracao)
VALUES (
           '160e8518-13b8-4b66-b375-bf2627a8385d',
           (SELECT id FROM usuario WHERE login = 'mariasouza'),
           (SELECT id FROM especialidade WHERE descricao = 'Medicina Intensiva'),
           'COREN-SP-67890',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Paciente
INSERT INTO paciente (id, id_usuario, criado_em, ultima_alteracao)
VALUES (
           'f08e1b43-b928-4325-84a9-91f783223acc',
           (SELECT id FROM usuario WHERE login = 'carloslima'),
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Unidade de saúde
INSERT INTO unidade_saude (id, nome, endereco, criado_em, ultima_alteracao)
VALUES (
           '37df601e-6cf3-4be4-871a-de5450bb783c',
           'Posto Central',
           'Rua das Flores, 123',
           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
       );

-- Consulta
INSERT INTO consulta (
    id, id_paciente, id_medico, id_enfermeiro, id_unidade_saude,
    observacao, data_consulta, status, criado_em, ultima_alteracao)
VALUES (
           'b1e2388e-582b-41c7-8dd4-8b0dbebe33aa',
           (SELECT p.id FROM paciente p JOIN usuario u ON p.id_usuario = u.id WHERE u.login = 'carloslima'),
           (SELECT m.id FROM medico m JOIN usuario u ON m.id_usuario = u.id WHERE u.login = 'joaosilva'),
           (SELECT e.id FROM enfermeiro e JOIN usuario u ON e.id_usuario = u.id WHERE u.login = 'mariasouza'),
           (SELECT id FROM unidade_saude WHERE nome = 'Posto Central'),
           'Consulta de rotina com exame clínico',
           DATEADD('DAY', 1, CURRENT_TIMESTAMP),
           'AGENDADA',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- Notificação
INSERT INTO notificacao (
    id, id_paciente, id_consulta, mensagem, data_envio, status, criado_em, ultima_alteracao)
VALUES (
           'a9005de0-88c0-44f6-8afc-b7d056c054d2',
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
