-- Script de criação do banco de dados
-- Execute este script para criar o banco de dados e as tabelas necessárias

-- Criar banco de dados (se não existir)
CREATE DATABASE IF NOT EXISTS crud_cliente
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar o banco de dados
USE crud_cliente;

-- Criar tabela de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    endereco VARCHAR(255),
    INDEX idx_cpf (cpf),
    INDEX idx_nome (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Criar tabela de contatos
CREATE TABLE IF NOT EXISTS contatos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_contato VARCHAR(50) NOT NULL,
    valor_contato VARCHAR(100) NOT NULL,
    observacao VARCHAR(255),
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    INDEX idx_cliente_id (cliente_id),
    INDEX idx_tipo_contato (tipo_contato)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

