@echo off
REM Script para Windows - Configuração automática do banco de dados
REM Este script executa os scripts SQL de criação e população do banco

REM Mudar para o diretório do projeto (pasta pai do scripts)
cd /d "%~dp0\.."

REM Salvar diretório do projeto
set PROJECT_DIR=%CD%
set SCRIPTS_DIR=%PROJECT_DIR%\scripts

echo ========================================
echo   Configuracao do Banco de Dados
echo   CRUD Cliente
echo ========================================
echo.
echo Diretorio do projeto: %PROJECT_DIR%
echo.

REM Verificar se o MySQL está acessível
echo Verificando conexao com MySQL...
set MYSQL_CMD=mysql
set MYSQL_FOUND=0

REM Primeiro, tentar usar MySQL do PATH
mysql --version >nul 2>&1
if not errorlevel 1 (
    set MYSQL_FOUND=1
    echo MySQL encontrado no PATH do sistema.
) else (
    echo MySQL nao encontrado no PATH.
    echo Tentando localizar MySQL em locais comuns...
    
    REM Tentar locais comuns do MySQL no Windows
    REM Procurar em Program Files
    for /d %%i in ("C:\Program Files\MySQL\MySQL Server *") do (
        if exist "%%i\bin\mysql.exe" (
            set MYSQL_CMD="%%i\bin\mysql.exe"
            set MYSQL_FOUND=1
            echo MySQL encontrado em: %%i\bin\
            goto :mysql_found
        )
    )
    
    REM Procurar em Program Files (x86)
    for /d %%i in ("C:\Program Files (x86)\MySQL\MySQL Server *") do (
        if exist "%%i\bin\mysql.exe" (
            set MYSQL_CMD="%%i\bin\mysql.exe"
            set MYSQL_FOUND=1
            echo MySQL encontrado em: %%i\bin\
            goto :mysql_found
        )
    )
    
    REM Procurar XAMPP
    if exist "C:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_CMD="C:\xampp\mysql\bin\mysql.exe"
        set MYSQL_FOUND=1
        echo MySQL encontrado em: C:\xampp\mysql\bin\
        goto :mysql_found
    )
    
    REM Procurar WAMP
    for /d %%i in ("C:\wamp64\bin\mysql\mysql*") do (
        if exist "%%i\bin\mysql.exe" (
            set MYSQL_CMD="%%i\bin\mysql.exe"
            set MYSQL_FOUND=1
            echo MySQL encontrado em: %%i\bin\
            goto :mysql_found
        )
    )
    
    :mysql_found
    if %MYSQL_FOUND%==0 (
        echo.
        echo ========================================
        echo   MySQL nao encontrado!
        echo ========================================
        echo.
        echo Opcoes:
        echo 1. Use MySQL Workbench:
        echo    - Abra o MySQL Workbench
        echo    - Conecte-se ao servidor
        echo    - Execute: %SCRIPTS_DIR%\01-create-database.sql
        echo    - Execute: %SCRIPTS_DIR%\02-populate-database.sql
        echo.
        echo 2. Adicione MySQL ao PATH do sistema
        echo.
        echo Pressione qualquer tecla para fechar...
        pause >nul
        exit /b 1
    )
)

REM Verificar se o comando funciona
%MYSQL_CMD% --version >nul 2>&1
if errorlevel 1 (
    echo ERRO: Nao foi possivel executar o MySQL!
    echo Pressione qualquer tecla para fechar...
    pause >nul
    exit /b 1
)

echo MySQL encontrado e funcionando!
echo.

REM Solicitar credenciais
set /p DB_USER="Usuario MySQL (padrao: root): "
if "%DB_USER%"=="" set DB_USER=root

set /p DB_PASS="Senha MySQL: "

echo.
echo Criando banco de dados e tabelas...
echo Executando: %SCRIPTS_DIR%\01-create-database.sql
%MYSQL_CMD% -u %DB_USER% -p%DB_PASS% < "%SCRIPTS_DIR%\01-create-database.sql"
if errorlevel 1 (
    echo.
    echo ERRO ao criar banco de dados!
    echo Verifique:
    echo - Se o MySQL esta rodando
    echo - Se o usuario e senha estao corretos
    echo - Se o arquivo existe: %SCRIPTS_DIR%\01-create-database.sql
    echo.
    pause
    exit /b 1
)

echo Banco de dados criado com sucesso!
echo.

echo Populando banco de dados com dados de exemplo...
echo Executando: %SCRIPTS_DIR%\02-populate-database.sql
%MYSQL_CMD% -u %DB_USER% -p%DB_PASS% < "%SCRIPTS_DIR%\02-populate-database.sql"
if errorlevel 1 (
    echo.
    echo ERRO ao popular banco de dados!
    echo Verifique:
    echo - Se o banco foi criado corretamente
    echo - Se o arquivo existe: %SCRIPTS_DIR%\02-populate-database.sql
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
echo Pressione qualquer tecla para fechar...
pause >nul

