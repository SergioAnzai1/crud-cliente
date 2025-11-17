#!/bin/bash
# Script para Linux/Mac - Configuração automática do banco de dados
# Este script executa os scripts SQL de criação e população do banco

# Obter o diretório do script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$( cd "$SCRIPT_DIR/.." && pwd )"
SCRIPTS_DIR="$PROJECT_DIR/scripts"

# Mudar para o diretório do projeto
cd "$PROJECT_DIR"

echo "========================================"
echo "  Configuração do Banco de Dados"
echo "  CRUD Cliente"
echo "========================================"
echo ""
echo "Diretório do projeto: $PROJECT_DIR"
echo ""

# Verificar se o MySQL está acessível
echo "Verificando conexão com MySQL..."
MYSQL_CMD="mysql"
MYSQL_FOUND=0

# Primeiro, tentar usar MySQL do PATH
if command -v mysql &> /dev/null; then
    MYSQL_FOUND=1
    echo "MySQL encontrado no PATH do sistema."
else
    echo "MySQL não encontrado no PATH."
    echo "Tentando localizar MySQL em locais comuns..."
    
    # Tentar locais comuns do MySQL no Linux/Mac
    # Linux - locais padrão
    if [ -f "/usr/bin/mysql" ]; then
        MYSQL_CMD="/usr/bin/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /usr/bin/mysql"
    elif [ -f "/usr/local/bin/mysql" ]; then
        MYSQL_CMD="/usr/local/bin/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /usr/local/bin/mysql"
    # Mac - Homebrew
    elif [ -f "/opt/homebrew/bin/mysql" ]; then
        MYSQL_CMD="/opt/homebrew/bin/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /opt/homebrew/bin/mysql"
    # Mac - Instalação padrão
    elif [ -f "/Applications/MySQL.app/Contents/MacOS/mysql" ]; then
        MYSQL_CMD="/Applications/MySQL.app/Contents/MacOS/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /Applications/MySQL.app/Contents/MacOS/mysql"
    elif [ -f "/usr/local/mysql/bin/mysql" ]; then
        MYSQL_CMD="/usr/local/mysql/bin/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /usr/local/mysql/bin/mysql"
    # XAMPP Linux
    elif [ -f "/opt/lampp/bin/mysql" ]; then
        MYSQL_CMD="/opt/lampp/bin/mysql"
        MYSQL_FOUND=1
        echo "MySQL encontrado em: /opt/lampp/bin/mysql"
    # Procurar em /usr/local/mysql (várias versões)
    elif [ -d "/usr/local/mysql" ]; then
        for mysql_bin in /usr/local/mysql*/bin/mysql; do
            if [ -f "$mysql_bin" ]; then
                MYSQL_CMD="$mysql_bin"
                MYSQL_FOUND=1
                echo "MySQL encontrado em: $mysql_bin"
                break
            fi
        done
    fi
    
    if [ $MYSQL_FOUND -eq 0 ]; then
        echo ""
        echo "========================================"
        echo "  MySQL não encontrado!"
        echo "========================================"
        echo ""
        echo "Opções:"
        echo "1. Use MySQL Workbench:"
        echo "   - Abra o MySQL Workbench"
        echo "   - Conecte-se ao servidor"
        echo "   - Execute: $SCRIPTS_DIR/01-create-database.sql"
        echo "   - Execute: $SCRIPTS_DIR/02-populate-database.sql"
        echo ""
        echo "2. Instale o MySQL ou adicione-o ao PATH do sistema"
        echo ""
        exit 1
    fi
fi

# Verificar se o comando encontrado funciona
if ! $MYSQL_CMD --version &> /dev/null; then
    echo "ERRO: Não foi possível executar o MySQL!"
    exit 1
fi

echo "MySQL encontrado e funcionando!"
echo ""

# Solicitar credenciais
read -p "Usuário MySQL (padrão: root): " DB_USER
DB_USER=${DB_USER:-root}

read -sp "Senha MySQL: " DB_PASS
echo ""

echo ""
echo "Criando banco de dados e tabelas..."
echo "Executando: $SCRIPTS_DIR/01-create-database.sql"
$MYSQL_CMD -u "$DB_USER" -p"$DB_PASS" < "$SCRIPTS_DIR/01-create-database.sql"
if [ $? -ne 0 ]; then
    echo ""
    echo "ERRO ao criar banco de dados!"
    echo "Verifique:"
    echo "- Se o MySQL está rodando"
    echo "- Se o usuário e senha estão corretos"
    echo "- Se o usuário tem permissão para criar bancos de dados"
    echo "- Se o arquivo existe: $SCRIPTS_DIR/01-create-database.sql"
    echo ""
    exit 1
fi

echo "Banco de dados criado com sucesso!"
echo ""

echo "Populando banco de dados com dados de exemplo..."
echo "Executando: $SCRIPTS_DIR/02-populate-database.sql"
$MYSQL_CMD -u "$DB_USER" -p"$DB_PASS" < "$SCRIPTS_DIR/02-populate-database.sql"
if [ $? -ne 0 ]; then
    echo ""
    echo "ERRO ao popular banco de dados!"
    echo "Verifique:"
    echo "- Se o banco foi criado corretamente"
    echo "- Se o arquivo existe: $SCRIPTS_DIR/02-populate-database.sql"
    echo ""
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

