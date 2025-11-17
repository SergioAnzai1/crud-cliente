@echo off
REM Script para Windows - Configuração automática do banco de dados
REM Este script executa os scripts SQL de criação e população do banco

echo ========================================
echo   Configuracao do Banco de Dados
echo   CRUD Cliente
echo ========================================
echo.

REM Verificar se o MySQL está acessível
echo Verificando conexao com MySQL...
mysql --version >nul 2>&1
if errorlevel 1 (
    echo ERRO: MySQL nao encontrado no PATH!
    echo Por favor, instale o MySQL ou adicione-o ao PATH do sistema.
    pause
    exit /b 1
)

echo MySQL encontrado!
echo.

REM Solicitar credenciais
set /p DB_USER="Usuario MySQL (padrao: root): "
if "%DB_USER%"=="" set DB_USER=root

set /p DB_PASS="Senha MySQL: "

echo.
echo Criando banco de dados e tabelas...
mysql -u %DB_USER% -p%DB_PASS% < scripts\01-create-database.sql
if errorlevel 1 (
    echo ERRO ao criar banco de dados!
    pause
    exit /b 1
)

echo Banco de dados criado com sucesso!
echo.

echo Populando banco de dados com dados de exemplo...
mysql -u %DB_USER% -p%DB_PASS% < scripts\02-populate-database.sql
if errorlevel 1 (
    echo ERRO ao popular banco de dados!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Configuracao concluida com sucesso!
echo ========================================
echo.
echo O banco de dados foi criado e populado.
echo Agora voce pode executar a aplicacao Spring Boot.
echo.
pause

