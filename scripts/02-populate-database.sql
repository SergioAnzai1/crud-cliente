-- Script de população do banco de dados com dados de exemplo
-- Execute este script após criar o banco de dados para popular com dados de teste

USE crud_cliente;

-- Limpar dados existentes e resetar AUTO_INCREMENT
DELETE FROM contatos;
DELETE FROM clientes;
ALTER TABLE clientes AUTO_INCREMENT = 1;
ALTER TABLE contatos AUTO_INCREMENT = 1;

-- Inserir clientes de exemplo
INSERT INTO clientes (nome, cpf, data_nascimento, endereco) VALUES
('João Silva', '123.456.789-00', '1990-05-15', 'Rua das Flores, 123 - São Paulo, SP'),
('Maria Santos', '987.654.321-00', '1985-08-22', 'Avenida Paulista, 1000 - São Paulo, SP'),
('Pedro Oliveira', '111.222.333-44', '1992-12-03', 'Rua do Comércio, 456 - Rio de Janeiro, RJ'),
('Ana Costa', '555.666.777-88', '1988-03-18', 'Rua das Palmeiras, 789 - Belo Horizonte, MG'),
('Carlos Pereira', '999.888.777-66', '1995-07-25', 'Avenida Atlântica, 200 - Rio de Janeiro, RJ'),
('Juliana Ferreira', '444.333.222-11', '1991-11-30', 'Rua da Praia, 321 - Florianópolis, SC'),
('Roberto Alves', '777.888.999-00', '1987-02-14', 'Rua Central, 654 - Porto Alegre, RS'),
('Fernanda Lima', '222.333.444-55', '1993-09-07', 'Avenida Beira Mar, 987 - Salvador, BA');

-- Inserir contatos de exemplo usando os IDs dos clientes inseridos
-- Contatos para João Silva (ID 1)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('TELEFONE', '(11) 98765-4321', 'Celular pessoal', (SELECT id FROM clientes WHERE cpf = '123.456.789-00')),
('EMAIL', 'joao.silva@email.com', 'E-mail principal', (SELECT id FROM clientes WHERE cpf = '123.456.789-00')),
('TELEFONE', '(11) 3456-7890', 'Telefone residencial', (SELECT id FROM clientes WHERE cpf = '123.456.789-00'));

-- Contatos para Maria Santos (ID 2)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('EMAIL', 'maria.santos@email.com', 'E-mail corporativo', (SELECT id FROM clientes WHERE cpf = '987.654.321-00')),
('TELEFONE', '(11) 91234-5678', 'Celular', (SELECT id FROM clientes WHERE cpf = '987.654.321-00'));

-- Contatos para Pedro Oliveira (ID 3)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('TELEFONE', '(21) 99876-5432', 'Celular', (SELECT id FROM clientes WHERE cpf = '111.222.333-44')),
('EMAIL', 'pedro.oliveira@email.com', 'E-mail pessoal', (SELECT id FROM clientes WHERE cpf = '111.222.333-44')),
('TELEFONE', '(21) 2345-6789', 'Telefone comercial', (SELECT id FROM clientes WHERE cpf = '111.222.333-44'));

-- Contatos para Ana Costa (ID 4)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('EMAIL', 'ana.costa@email.com', 'E-mail principal', (SELECT id FROM clientes WHERE cpf = '555.666.777-88')),
('TELEFONE', '(31) 98765-4321', 'Celular', (SELECT id FROM clientes WHERE cpf = '555.666.777-88'));

-- Contatos para Carlos Pereira (ID 5)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('TELEFONE', '(21) 91234-5678', 'Celular pessoal', (SELECT id FROM clientes WHERE cpf = '999.888.777-66')),
('EMAIL', 'carlos.pereira@email.com', 'E-mail', (SELECT id FROM clientes WHERE cpf = '999.888.777-66'));

-- Contatos para Juliana Ferreira (ID 6)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('EMAIL', 'juliana.ferreira@email.com', 'E-mail principal', (SELECT id FROM clientes WHERE cpf = '444.333.222-11')),
('TELEFONE', '(48) 98765-4321', 'Celular', (SELECT id FROM clientes WHERE cpf = '444.333.222-11')),
('TELEFONE', '(48) 3456-7890', 'Telefone residencial', (SELECT id FROM clientes WHERE cpf = '444.333.222-11'));

-- Contatos para Roberto Alves (ID 7)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('TELEFONE', '(51) 99876-5432', 'Celular', (SELECT id FROM clientes WHERE cpf = '777.888.999-00')),
('EMAIL', 'roberto.alves@email.com', 'E-mail', (SELECT id FROM clientes WHERE cpf = '777.888.999-00'));

-- Contatos para Fernanda Lima (ID 8)
INSERT INTO contatos (tipo_contato, valor_contato, observacao, cliente_id) VALUES
('EMAIL', 'fernanda.lima@email.com', 'E-mail principal', (SELECT id FROM clientes WHERE cpf = '222.333.444-55')),
('TELEFONE', '(71) 98765-4321', 'Celular pessoal', (SELECT id FROM clientes WHERE cpf = '222.333.444-55')),
('TELEFONE', '(71) 2345-6789', 'Telefone comercial', (SELECT id FROM clientes WHERE cpf = '222.333.444-55'));

-- Verificar dados inseridos
SELECT 'Clientes inseridos:' AS Status;
SELECT COUNT(*) AS Total FROM clientes;

SELECT 'Contatos inseridos:' AS Status;
SELECT COUNT(*) AS Total FROM contatos;

SELECT 'Resumo por cliente:' AS Status;
SELECT 
    c.id,
    c.nome,
    COUNT(ct.id) AS total_contatos
FROM clientes c
LEFT JOIN contatos ct ON c.id = ct.cliente_id
GROUP BY c.id, c.nome
ORDER BY c.id;

