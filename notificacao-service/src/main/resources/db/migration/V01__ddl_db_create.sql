-- Cria o schema se ainda não existir
CREATE SCHEMA IF NOT EXISTS medisync;

-- Garante que os objetos criados estarão no schema medisync
SET search_path TO medisync;

-- Garante suporte à geração de UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela de notificações
CREATE TABLE IF NOT EXISTS notificacao (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_paciente UUID NOT NULL,
    id_consulta UUID NOT NULL,
    mensagem TEXT NOT NULL,
    data_envio TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultima_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
