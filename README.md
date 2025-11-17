# CRUD Cliente - Sistema de Gestão de Clientes e Contatos

Sistema web desenvolvido em Spring Boot para gerenciamento de clientes e seus contatos (telefones e e-mails).

## 🚀 Tecnologias

- **Backend:** Spring Boot 3.5.7, Java 21
- **Banco de Dados:** MySQL
- **Frontend:** HTML, CSS, JavaScript (Vanilla)
- **Build Tool:** Maven

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- Java 21 ou superior
- Maven 3.6+ (ou use o Maven Wrapper incluído)
- MySQL 8.0+ instalado e em execução
- Git (para clonar o repositório)

## ⚙️ Configuração do Banco de Dados

### Opção 1: Script Automático (Recomendado)

#### Windows:
```bash
scripts\setup-database.bat
```

#### Linux/Mac:
```bash
chmod +x scripts/setup-database.sh
./scripts/setup-database.sh
```

### Opção 2: Manual

1. Conecte-se ao MySQL:
   ```bash
   mysql -u root -p
   ```

2. Execute os scripts na ordem:
   ```sql
   source scripts/01-create-database.sql
   source scripts/02-populate-database.sql
   ```

### Opção 3: Via MySQL Workbench

1. Abra o MySQL Workbench
2. Conecte-se ao servidor MySQL
3. Abra e execute `scripts/01-create-database.sql`
4. Abra e execute `scripts/02-populate-database.sql`

**📖 Para mais detalhes, consulte:** [scripts/README.md](scripts/README.md)

## 🔧 Configuração da Aplicação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SergioAnzai1/crud-cliente.git
   cd crud-cliente
   ```

2. **Configure o banco de dados:**
   
   Edite o arquivo `src/main/resources/application.properties` se necessário:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/crud_cliente?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Sao_Paulo
   spring.datasource.username=root
   spring.datasource.password=sua_senha_aqui
   ```

3. **Execute o banco de dados:**
   - Certifique-se de que o MySQL está rodando
   - Execute os scripts de criação e população (veja seção acima)

## 🏃 Executando a Aplicação

### Usando Maven Wrapper (Recomendado):

#### Windows:
```bash
mvnw.cmd spring-boot:run
```

#### Linux/Mac:
```bash
./mvnw spring-boot:run
```

### Usando Maven instalado:
```bash
mvn spring-boot:run
```

### Usando IDE:
- Importe o projeto como projeto Maven
- Execute a classe `CrudClienteApplication.java`

## 🌐 Acessando a Aplicação

Após iniciar a aplicação, acesse:

- **URL:** http://localhost:8080
- **Página Principal:** Lista de clientes
- **Adicionar Cliente:** http://localhost:8080/adicionar-cliente.html

## 📚 Funcionalidades

### Clientes
- ✅ Listar todos os clientes
- ✅ Adicionar novo cliente
- ✅ Buscar cliente por Nome ou CPF
- ✅ Editar cliente existente (via modal)
- ✅ Excluir cliente
- ✅ Validação de CPF (formato e unicidade)
- ✅ Validação de data de nascimento

### Contatos
- ✅ Adicionar contato (telefone ou e-mail) a um cliente
- ✅ Visualizar todos os contatos de um cliente
- ✅ Editar contato existente
- ✅ Excluir contato
- ✅ Múltiplos contatos por cliente

## 📁 Estrutura do Projeto

```
crud-cliente/
├── scripts/                  # Scripts SQL de banco de dados
│   ├── 01-create-database.sql
│   ├── 02-populate-database.sql
│   ├── setup-database.bat    # Script Windows
│   ├── setup-database.sh     # Script Linux/Mac
│   └── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/empresa/crud/cliente/
│   │   │       ├── controller/    # Controllers REST
│   │   │       ├── service/       # Lógica de negócio
│   │   │       ├── repository/    # Repositórios JPA
│   │   │       ├── model/         # Entidades
│   │   │       ├── DTO/           # Data Transfer Objects
│   │   │       └── exception/     # Tratamento de exceções
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/            # Frontend
│   │           ├── index.html
│   │           ├── adicionar-cliente.html
│   │           ├── css/
│   │           └── js/
│   └── test/                      # Testes
└── pom.xml
```

## 🧪 Testes Automatizados

O projeto inclui testes automatizados para garantir a qualidade do código:

### Executar todos os testes:
```bash
# Windows
mvnw.cmd test

# Linux/Mac
./mvnw test
```

### Cobertura de Testes:
- ✅ **Testes Unitários** - Services (ClienteService, ContatoService)
- ✅ **Testes de Integração** - Controllers REST (ClienteController, ContatoController)
- ✅ **Validações** - Testes de regras de negócio e validações
- ✅ **Casos de Erro** - Testes de exceções e erros

**📖 Para mais detalhes:** [src/test/README.md](src/test/README.md)

## 📊 Dados de Teste

O script de população (`02-populate-database.sql`) cria:
- **8 clientes** de exemplo
- **Múltiplos contatos** (telefones e e-mails) para cada cliente

## 🐛 Troubleshooting

### Erro de conexão com banco de dados
- Verifique se o MySQL está rodando
- Confirme as credenciais no `application.properties`
- Certifique-se de que o banco `crud_cliente` foi criado

### Porta 8080 já em uso
- Altere a porta no `application.properties`:
  ```properties
  server.port=8081
  ```

### Erro ao executar scripts SQL
- Verifique se o MySQL está acessível
- Confirme que o usuário tem permissões para criar banco de dados
- Veja mais detalhes em [scripts/README.md](scripts/README.md)

## 📝 API REST

### Clientes
- `GET /clientes` - Lista todos os clientes
- `GET /clientes/buscar?termo=...` - Busca por nome ou CPF
- `POST /clientes` - Cria novo cliente
- `PUT /clientes/{id}` - Atualiza cliente
- `DELETE /clientes/{id}` - Exclui cliente

### Contatos
- `GET /contatos/cliente/{clienteId}` - Lista contatos de um cliente
- `POST /contatos` - Cria novo contato
- `PUT /contatos/{id}` - Atualiza contato
- `DELETE /contatos/{id}` - Exclui contato

## 👨‍💻 Desenvolvido por

Sergio Anzai

## 📄 Licença

Este projeto é um desafio/exercício de desenvolvimento.

