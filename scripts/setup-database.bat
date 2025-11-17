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
set MYSQL_CMD=mysql

mysql --version >nul 2>&1
if errorlevel 1 (
    echo MySQL nao encontrado no PATH.
    echo Tentando localizar MySQL em locais comuns...
    
    REM Tentar locais comuns do MySQL no Windows
    if exist "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" (
        set MYSQL_CMD="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files\MySQL\MySQL Server 8.0\bin\
    ) else if exist "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe" (
        set MYSQL_CMD="C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files\MySQL\MySQL Server 8.4\bin\
    ) else if exist "C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe" (
        set MYSQL_CMD="C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\
    ) else if exist "C:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_CMD="C:\xampp\mysql\bin\mysql.exe"
        echo MySQL encontrado em: C:\xampp\mysql\bin\
    ) else (
        echo.
        echo ========================================
        echo   MySQL nao encontrado!
        echo ========================================
        echo.
        echo Opcoes:
        echo 1. Use MySQL Workbench:
        echo    - Abra o MySQL Workbench
        echo    - Conecte-se ao servidor
        echo    - Execute: scripts\01-create-database.sql
        echo    - Execute: scripts\02-populate-database.sql
        echo.
        echo 2. Adicione MySQL ao PATH do sistema
        echo.
        pause
        exit /b 1
    )
)

echo MySQL encontrado e funcionando!
echo.

REM Solicitar credenciais
set /p DB_USER="Usuario MySQL (padrao: root): "
if "%DB_USER%"=="" set DB_USER=root

set /p DB_PASS="Senha MySQL: "

echo.
echo Criando banco de dados e tabelas...
%MYSQL_CMD% -u %DB_USER% -p%DB_PASS% < scripts\01-create-database.sql
if errorlevel 1 (
    echo.
    echo ERRO ao criar banco de dados!
    echo Verifique:
    echo - Se o MySQL esta rodando
    echo - Se o usuario e senha estao corretos
    echo.
    pause
    exit /b 1
)

echo Banco de dados criado com sucesso!
echo.

echo Populando banco de dados com dados de exemplo...
%MYSQL_CMD% -u %DB_USER% -p%DB_PASS% < scripts\02-populate-database.sql
if errorlevel 1 (
    echo.
    echo ERRO ao popular banco de dados!
    echo Verifique se o banco foi criado corretamente.
    echo.
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

