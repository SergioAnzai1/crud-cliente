# Testes Automatizados

Este diretório contém os testes automatizados do projeto CRUD Cliente.

## 📋 Estrutura de Testes

### Testes Unitários
- **ClienteServiceTest** - Testa a lógica de negócio do serviço de clientes
- **ContatoServiceTest** - Testa a lógica de negócio do serviço de contatos

### Testes de Integração
- **ClienteControllerTest** - Testa os endpoints REST de clientes
- **ContatoControllerTest** - Testa os endpoints REST de contatos

## 🚀 Como Executar os Testes

### Executar todos os testes:
```bash
# Windows
mvnw.cmd test

# Linux/Mac
./mvnw test
```

### Executar testes específicos:
```bash
# Testes de serviço
mvnw.cmd test -Dtest=ClienteServiceTest
mvnw.cmd test -Dtest=ContatoServiceTest

# Testes de controller
mvnw.cmd test -Dtest=ClienteControllerTest
mvnw.cmd test -Dtest=ContatoControllerTest
```

### Executar via IDE:
- **IntelliJ IDEA:** Clique com botão direito no arquivo de teste e selecione "Run Test"
- **Eclipse:** Clique com botão direito no arquivo de teste e selecione "Run As > JUnit Test"
- **VS Code:** Use a extensão Java Test Runner

## 📊 Cobertura de Testes

### ClienteService
- ✅ Salvar cliente com sucesso
- ✅ Salvar cliente com CPF duplicado (validação)
- ✅ Salvar cliente nulo (validação)
- ✅ Atualizar cliente com sucesso
- ✅ Atualizar cliente não encontrado
- ✅ Deletar cliente com sucesso
- ✅ Deletar cliente não encontrado
- ✅ Buscar todos os clientes
- ✅ Buscar por nome ou CPF
- ✅ Buscar cliente por ID

### ContatoService
- ✅ Salvar contato com sucesso
- ✅ Atualizar contato com sucesso
- ✅ Atualizar contato não encontrado
- ✅ Deletar contato com sucesso
- ✅ Deletar contato não encontrado
- ✅ Buscar contatos por cliente ID

### ClienteController
- ✅ POST /clientes - Criar cliente
- ✅ PUT /clientes/{id} - Atualizar cliente
- ✅ DELETE /clientes/{id} - Deletar cliente
- ✅ GET /clientes - Listar todos
- ✅ GET /clientes/buscar?termo=... - Buscar por nome/CPF
- ✅ Validação de dados inválidos

### ContatoController
- ✅ POST /contatos - Criar contato
- ✅ PUT /contatos/{id} - Atualizar contato
- ✅ DELETE /contatos/{id} - Deletar contato
- ✅ GET /contatos/cliente/{clienteId} - Listar contatos
- ✅ Validação de dados inválidos

## 🛠️ Tecnologias Utilizadas

- **JUnit 5** - Framework de testes
- **Mockito** - Framework de mocking
- **Spring Boot Test** - Testes de integração
- **MockMvc** - Testes de controllers REST
- **H2 Database** - Banco de dados em memória para testes

## 📝 Configuração

Os testes utilizam o arquivo `application-test.properties` que configura:
- Banco de dados H2 em memória
- DDL automático (create-drop)
- Logs de SQL desabilitados

## ✅ Boas Práticas Aplicadas

1. **Arrange-Act-Assert (AAA)** - Estrutura clara dos testes
2. **Testes isolados** - Cada teste é independente
3. **Nomes descritivos** - Nomes de métodos explicam o que está sendo testado
4. **Mocks apropriados** - Uso de mocks para isolar unidades
5. **Validação completa** - Testa casos de sucesso e erro
6. **Cobertura de requisitos** - Testa todos os requisitos funcionais

## 🎯 Próximos Passos (Opcional)

Para aumentar ainda mais a cobertura, você pode adicionar:
- Testes de integração com banco de dados real (usando Testcontainers)
- Testes de validação de regras de negócio mais complexas
- Testes de performance
- Testes end-to-end (E2E)

