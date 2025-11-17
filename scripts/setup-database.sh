#!/bin/bash
# Script para Linux/Mac - Configuração automática do banco de dados
# Este script executa os scripts SQL de criação e população do banco

echo "========================================"
echo "  Configuração do Banco de Dados"
echo "  CRUD Cliente"
echo "========================================"
echo ""

# Verificar se o MySQL está acessível
echo "Verificando conexão com MySQL..."
if ! command -v mysql &> /dev/null; then
    echo "ERRO: MySQL não encontrado no PATH!"
    echo "Por favor, instale o MySQL ou adicione-o ao PATH do sistema."
    exit 1
fi

echo "MySQL encontrado!"
echo ""

# Solicitar credenciais
read -p "Usuário MySQL (padrão: root): " DB_USER
DB_USER=${DB_USER:-root}

read -sp "Senha MySQL: " DB_PASS
echo ""

echo ""
echo "Criando banco de dados e tabelas..."
mysql -u "$DB_USER" -p"$DB_PASS" < scripts/01-create-database.sql
if [ $? -ne 0 ]; then
    echo "ERRO ao criar banco de dados!"
    exit 1
fi

echo "Banco de dados criado com sucesso!"
echo ""

echo "Populando banco de dados com dados de exemplo..."
mysql -u "$DB_USER" -p"$DB_PASS" < scripts/02-populate-database.sql
if [ $? -ne 0 ]; then
    echo "ERRO ao popular banco de dados!"
    exit 1
fi

echo ""
echo "========================================"
echo "  Configuração concluída com sucesso!"
echo "========================================"
echo ""
echo "O banco de dados foi criado e populado."
echo "Agora você pode executar a aplicação Spring Boot."
echo ""

