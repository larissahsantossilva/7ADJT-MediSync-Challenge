-- Criação do schema
CREATE SCHEMA IF NOT EXISTS medisync;

-- Garante que tudo será criado dentro do schema medisync
SET search_path TO medisync;

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Definição do tipo ENUM para especialidades médicas
CREATE TYPE especialidade_enum AS ENUM (
    'Acupuntura',
    'Alergia e Imunologia',
    'Anatomia Patológica',
    'Anestesiologia',
    'Angiologia',
    'Cancerologia',
    'Cardiologia',
    'Cirurgia Cardiovascular',
    'Cirurgia da Mão',
    'Cirurgia de Cabeça e Pescoço',
    'Cirurgia do Aparelho Digestivo',
    'Cirurgia Geral',
    'Cirurgia Pediátrica',
    'Cirurgia Plástica',
    'Cirurgia Torácica',
    'Cirurgia Vascular',
    'Clínica Médica',
    'Coloproctologia',
    'Dermatologia',
    'Endocrinologia e Metabologia',
    'Endoscopia',
    'Gastroenterologia',
    'Genética Médica',
    'Geriatria',
    'Ginecologia e Obstetrícia',
    'Hematologia e Hemoterapia',
    'Homeopatia',
    'Infectologia',
    'Mastologia',
    'Medicina de Emergência',
    'Medicina de Família e Comunidade',
    'Medicina do Trabalho',
    'Medicina do Tráfego',
    'Medicina Esportiva',
    'Medicina Física e Reabilitação',
    'Medicina Intensiva',
    'Medicina Legal e Perícia Médica',
    'Medicina Nuclear',
    'Medicina Preventiva e Social',
    'Nefrologia',
    'Neurocirurgia',
    'Neurologia',
    'Nutrologia',
    'Oftalmologia',
    'Ortopedia e Traumatologia',
    'Otorrinolaringologia',
    'Patologia',
    'Patologia Clínica/Medicina Laboratorial',
    'Pediatria',
    'Pneumologia',
    'Psiquiatria',
    'Radiologia e Diagnóstico por Imagem',
    'Radioterapia',
    'Reumatologia',
    'Urologia'
    );

-- Tabela de usuários
CREATE TABLE usuario (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        nome VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        login VARCHAR(100) NOT NULL,
                        senha VARCHAR(255) NOT NULL,
                        cpf VARCHAR(11) NOT NULL,
                        data_nascimento DATE NOT NULL,
                        telefone VARCHAR(20),
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT uk_usuario_email UNIQUE (email),
                        CONSTRAINT uk_usuario_login UNIQUE (login),
                        CONSTRAINT uk_usuario_cpf UNIQUE (cpf)
);

-- Tabela de médicos
CREATE TABLE medico (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_usuario UUID NOT NULL,
                        especialidade especialidade_enum NOT NULL,
                        crm VARCHAR(50) NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_medico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
                        CONSTRAINT uk_medico_usuario UNIQUE (id_usuario)
);

-- Tabela de pacientes
CREATE TABLE paciente (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_usuario UUID NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_paciente_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
                        CONSTRAINT uk_paciente_usuario UNIQUE (id_usuario)
);

-- Tabela de enfermeiros
CREATE TABLE enfermeiro (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_usuario UUID NOT NULL,
                        especialidade especialidade_enum NOT NULL,
                        coren VARCHAR(50) NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_enfermeiro_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
                        CONSTRAINT uk_enfermeiro_usuario UNIQUE (id_usuario)
);

-- Tabela de unidades de saúde
CREATE TABLE unidade_saude (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        nome VARCHAR(255) NOT NULL,
                        endereco VARCHAR(255) NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de consultas
CREATE TABLE consulta (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_paciente UUID NOT NULL,
                        id_medico UUID NOT NULL,
                        id_enfermeiro UUID NOT NULL,
                        id_unidade_saude UUID NOT NULL,
                        observacao TEXT,
                        data_consulta TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_consulta_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id),
                        CONSTRAINT fk_consulta_medico FOREIGN KEY (id_medico) REFERENCES medico(id),
                        CONSTRAINT fk_consulta_enfermeiro FOREIGN KEY (id_enfermeiro) REFERENCES enfermeiro(id),
                        CONSTRAINT fk_consulta_unidade FOREIGN KEY (id_unidade_saude) REFERENCES unidade_saude(id)
);

-- Tabela de notificações
CREATE TABLE notificacao (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_paciente UUID NOT NULL,
                        id_consulta UUID NOT NULL,
                        mensagem TEXT NOT NULL,
                        data_envio TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_notificacao_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id),
                        CONSTRAINT fk_notificacao_consulta FOREIGN KEY (id_consulta) REFERENCES consulta(id)
);
