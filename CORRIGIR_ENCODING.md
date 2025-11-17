# 🔧 Como Corrigir Caracteres Especiais (Encoding UTF-8)

## ✅ O que foi corrigido:

1. **Configurações do Spring Boot** (`application.properties`):
   - Encoding HTTP: UTF-8
   - Encoding de requisições: UTF-8
   - Encoding do banco de dados: UTF-8

2. **Classe de Configuração** (`EncodingConfig.java`):
   - Garante que todas as respostas JSON e String usem UTF-8

3. **Banco de Dados**:
   - Já estava configurado com `utf8mb4` (correto)

## 🔄 Passos para Aplicar as Correções:

### 1. **Reinicie a Aplicação Spring Boot**
   - Pare a aplicação (Ctrl+C)
   - Inicie novamente: `mvnw spring-boot:run`

### 2. **Recrie o Banco de Dados** (IMPORTANTE!)
   
   Os dados que já estão no banco podem estar com encoding incorreto. Para corrigir:

   **Opção A: Usar o script de setup (Recomendado)**
   ```bash
   # Windows
   scripts\setup-database.bat
   
   # Linux/Mac
   ./scripts/setup-database.sh
   ```
   
   Isso vai:
   - Limpar os dados antigos
   - Recriar o banco com encoding correto
   - Popular com dados de exemplo corretos

   **Opção B: Manualmente no MySQL**
   ```sql
   -- Conecte-se ao MySQL
   mysql -u root -p
   
   -- Delete o banco e recrie
   DROP DATABASE IF EXISTS crud_cliente;
   
   -- Execute os scripts
   source scripts/01-create-database.sql
   source scripts/02-populate-database.sql
   ```

### 3. **Verifique no Navegador**
   - Limpe o cache do navegador (Ctrl+Shift+Delete)
   - Ou use modo anônimo/privado
   - Acesse: http://localhost:8080
   - Os caracteres especiais devem aparecer corretamente agora

## 🎯 Verificação:

Após seguir os passos acima, você deve ver:
- ✅ "João Silva" (não "Jo Fúo Silva")
- ✅ "São Paulo" (não "S úo Paulo")
- ✅ Todos os acentos e caracteres especiais corretos

## ⚠️ Se ainda não funcionar:

1. **Verifique o encoding do arquivo HTML:**
   - Abra `index.html` no editor
   - Salve novamente como UTF-8 (sem BOM)

2. **Verifique o navegador:**
   - Pressione F12 (DevTools)
   - Vá em Network > Headers
   - Verifique se `Content-Type` tem `charset=UTF-8`

3. **Verifique o banco de dados:**
   ```sql
   -- Verificar encoding do banco
   SHOW CREATE DATABASE crud_cliente;
   
   -- Deve mostrar: CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
   ```

## 📝 Nota:

Se você já tinha dados no banco antes das correções, eles podem estar salvos com encoding incorreto. Por isso é importante recriar o banco usando os scripts fornecidos.

