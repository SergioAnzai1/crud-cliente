# Scripts de Banco de Dados

Este diretório contém scripts SQL para criação e população do banco de dados do projeto CRUD Cliente.

## Pré-requisitos

- MySQL instalado e em execução
- Acesso ao MySQL com permissões para criar banco de dados e tabelas
- Usuário e senha configurados (padrão: root/root)

## Como usar

### Opção 1: Executar via linha de comando MySQL

1. Abra o terminal/command prompt
2. Conecte-se ao MySQL:
   ```bash
   mysql -u root -p
   ```
   (Digite a senha quando solicitado)

3. Execute os scripts na ordem:
   ```sql
   source scripts/01-create-database.sql
   source scripts/02-populate-database.sql
   ```

### Opção 2: Executar via MySQL Workbench ou outro cliente SQL

1. Abra o MySQL Workbench (ou outro cliente SQL)
2. Conecte-se ao servidor MySQL
3. Abra e execute o arquivo `01-create-database.sql`
4. Abra e execute o arquivo `02-populate-database.sql`

### Opção 3: Executar via linha de comando (sem entrar no MySQL)

```bash
# Windows
mysql -u root -p < scripts/01-create-database.sql
mysql -u root -p < scripts/02-populate-database.sql

# Linux/Mac
mysql -u root -p < scripts/01-create-database.sql
mysql -u root -p < scripts/02-populate-database.sql
```

## Estrutura dos Scripts

### 01-create-database.sql
- Cria o banco de dados `crud_cliente`
- Cria as tabelas `clientes` e `contatos`
- Define índices e chaves estrangeiras

### 02-populate-database.sql
- Insere 8 clientes de exemplo
- Insere múltiplos contatos para cada cliente
- Inclui verificações finais dos dados inseridos

## Dados de Exemplo

O script de população cria:
- **8 clientes** com dados variados
- **Múltiplos contatos** (telefones e e-mails) para cada cliente
- Dados distribuídos em diferentes cidades do Brasil

## Configuração do Application.properties

Certifique-se de que o arquivo `src/main/resources/application.properties` está configurado corretamente:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud_cliente?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root
```

**Nota:** Se você usar credenciais diferentes, atualize o arquivo `application.properties` antes de executar a aplicação.

## Verificação

Após executar os scripts, você pode verificar se tudo foi criado corretamente:

```sql
USE crud_cliente;

-- Ver clientes
SELECT * FROM clientes;

-- Ver contatos
SELECT * FROM contatos;

-- Ver contatos por cliente
SELECT 
    c.nome AS Cliente,
    ct.tipo_contato AS Tipo,
    ct.valor_contato AS Contato,
    ct.observacao AS Observacao
FROM clientes c
LEFT JOIN contatos ct ON c.id = ct.cliente_id
ORDER BY c.nome, ct.tipo_contato;
```

## Limpar Dados (Opcional)

Se quiser limpar os dados de exemplo e começar do zero:

```sql
USE crud_cliente;
DELETE FROM contatos;
DELETE FROM clientes;
```

Ou execute o script de criação novamente (ele usa `CREATE TABLE IF NOT EXISTS`, então não recriará se já existir).

## Troubleshooting

### Erro: "Access denied"
- Verifique se o usuário tem permissões para criar banco de dados
- Tente executar como administrador

### Erro: "Database already exists"
- Isso é normal se o banco já existe
- O script usa `CREATE DATABASE IF NOT EXISTS`, então é seguro executar novamente

### Erro: "Table already exists"
- Isso é normal se as tabelas já existem
- Os scripts usam `CREATE TABLE IF NOT EXISTS`, então é seguro executar novamente

