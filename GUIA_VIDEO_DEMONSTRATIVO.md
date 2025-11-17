# 🎥 Guia para Vídeo Demonstrativo

## 📋 Estrutura Recomendada do Vídeo (5-10 minutos)

### 1. **Introdução (30 segundos)**
- Apresente-se brevemente
- Explique o que será demonstrado: "Sistema CRUD de Clientes e Contatos"
- Mencione as tecnologias principais (Spring Boot, MySQL, HTML/CSS/JavaScript)

### 2. **Setup e Configuração (1-2 minutos)**
- Mostre a estrutura do projeto no IDE
- Execute o script de setup do banco de dados (`setup-database.bat` ou `.sh`)
- Mostre que o banco foi criado e populado com sucesso
- Inicie a aplicação Spring Boot (`mvnw spring-boot:run` ou pela IDE)
- Mostre que a aplicação está rodando (console do Spring Boot)

### 3. **Demonstração das Funcionalidades (4-6 minutos)**

#### 3.1. **Listagem de Clientes**
- Abra o navegador em `http://localhost:8080`
- Mostre a lista de clientes carregada
- Demonstre o campo de busca (buscar por nome ou CPF)
- Mostre os resultados da busca

#### 3.2. **Cadastro de Cliente**
- Clique em "Adicionar Cliente"
- Preencha o formulário com dados de exemplo
- Mostre as validações (tente enviar com campos vazios)
- Salve um cliente válido
- Mostre que o cliente aparece na lista

#### 3.3. **Edição de Cliente**
- Clique no botão "Editar" de um cliente
- Mostre o modal abrindo com os dados preenchidos
- Altere alguns dados
- Salve e mostre que as alterações foram aplicadas

#### 3.4. **Gerenciamento de Contatos**
- Clique em "Adicionar Contato" de um cliente
- Mostre o modal de adicionar contato
- Adicione um telefone e um e-mail
- Clique em "Ver Contatos" para mostrar a lista
- Demonstre a edição de um contato (botão "Editar" no modal)
- Demonstre a exclusão de um contato

#### 3.5. **Exclusão de Cliente**
- Clique em "Excluir" de um cliente
- Mostre a confirmação
- Confirme e mostre que o cliente foi removido
- (Opcional) Mostre que os contatos também foram removidos (cascata)

### 4. **Testes Automatizados (1-2 minutos)**
- Abra o terminal/IDE
- Execute os testes: `mvnw test` ou `mvnw.cmd test`
- Mostre que todos os testes passaram
- (Opcional) Mostre rapidamente a estrutura dos testes

### 5. **Encerramento (30 segundos)**
- Resuma brevemente o que foi demonstrado
- Mencione os principais recursos implementados
- Agradeça

---

## 🎤 Dicas para Narrar o Vídeo

### ✅ **O QUE FALAR:**

1. **Durante o Setup:**
   - "Agora vou executar o script de setup do banco de dados..."
   - "O script criou o banco e populou com dados de exemplo..."
   - "Iniciando a aplicação Spring Boot..."

2. **Durante as Demonstrações:**
   - "Vou adicionar um novo cliente..."
   - "Aqui podemos ver a validação funcionando..."
   - "Agora vou editar este cliente usando o modal..."
   - "Vou adicionar contatos para este cliente..."
   - "A busca funciona tanto por nome quanto por CPF..."

3. **Durante os Testes:**
   - "Vou executar os testes automatizados..."
   - "Como podemos ver, todos os testes passaram com sucesso..."

### ❌ **O QUE EVITAR:**

- Não fique em silêncio por muito tempo
- Não fale muito rápido
- Não use termos muito técnicos sem explicar
- Não pule etapas importantes

---

## 📹 Dicas Técnicas de Gravação

### **Ferramentas Recomendadas:**
- **OBS Studio** (gratuito, multiplataforma)
- **Windows Game Bar** (Windows 10/11 - Win+G)
- **QuickTime** (Mac)
- **Loom** (online, fácil de usar)

### **Configurações:**
- Resolução: 1920x1080 (Full HD) ou 1280x720 (HD)
- FPS: 30 ou 60
- Áudio: Use um microfone de boa qualidade
- Área de gravação: Tela inteira ou janela do navegador/IDE

### **Antes de Gravar:**
- ✅ Feche aplicativos desnecessários
- ✅ Limpe a área de trabalho
- ✅ Teste o áudio e vídeo
- ✅ Prepare dados de exemplo
- ✅ Certifique-se de que tudo está funcionando

---

## 🎯 Checklist do Vídeo

### **Funcionalidades que DEVEM aparecer:**
- [ ] Listagem de clientes
- [ ] Busca por nome ou CPF
- [ ] Cadastro de cliente (com validação)
- [ ] Edição de cliente (modal)
- [ ] Exclusão de cliente
- [ ] Adicionar contato (modal)
- [ ] Ver contatos (modal)
- [ ] Editar contato (modal)
- [ ] Excluir contato
- [ ] Execução dos testes automatizados

### **Extras (opcional, mas valoriza):**
- [ ] Mostrar a estrutura do código
- [ ] Mostrar o banco de dados (MySQL Workbench)
- [ ] Mostrar os logs do Spring Boot
- [ ] Mostrar a responsividade (redimensionar janela)

---

## ⏱️ Tempo Estimado por Seção

| Seção | Tempo |
|-------|-------|
| Introdução | 30s |
| Setup | 1-2min |
| Listagem e Busca | 1min |
| Cadastro | 1min |
| Edição | 1min |
| Contatos (adicionar, ver, editar, excluir) | 2min |
| Exclusão | 30s |
| Testes | 1-2min |
| Encerramento | 30s |
| **TOTAL** | **8-10min** |

---

## 💡 Dicas Finais

1. **Faça um ensaio antes** de gravar a versão final
2. **Fale de forma natural**, como se estivesse explicando para um colega
3. **Use pausas** para dar tempo do espectador acompanhar
4. **Destaque os pontos fortes** do projeto (modais, validações, testes)
5. **Se errar**, pause, respire e continue (pode editar depois)
6. **Mantenha o foco** nas funcionalidades principais

---

## 📝 Roteiro Resumido

```
1. "Olá, meu nome é [seu nome] e vou demonstrar o sistema CRUD de Clientes e Contatos..."
2. [Executa setup do banco]
3. [Inicia aplicação]
4. [Abre navegador]
5. "Aqui temos a lista de clientes..."
6. [Demonstra busca]
7. [Adiciona cliente]
8. [Edita cliente]
9. [Adiciona contatos]
10. [Ver/editar/excluir contatos]
11. [Exclui cliente]
12. [Executa testes]
13. "Obrigado por assistir!"
```

---

**Boa sorte com a gravação! 🎬**

