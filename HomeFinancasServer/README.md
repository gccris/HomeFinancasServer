# HomeFinancasServer (springboot-clean-jwt-postgres)

Projeto Spring Boot (clean architecture) com JWT e PostgreSQL. Inclui Dockerfile, docker-compose e configuração para testes com H2.

Resumo rápido:
- API REST para cadastro/autenticação de usuários.
# HomeFinancasServer (springboot-clean-jwt-postgres)

Projeto exemplo em Spring Boot seguindo princípios de Clean Architecture, com autenticação JWT e integração com PostgreSQL.

**Resumo:** API REST para gerenciamento de usuários e autenticação via JWT, preparada para execução com Docker (Postgres) e para execução local via Maven. Testes usam H2 in-memory.

**Status:** Código-fonte fornecido; ver `src/` para implementação.

---

## Índice
- **Visão Geral**
- **Funcionalidades**
- **Stack Tecnológica**
- **Requisitos**
- **Variáveis de ambiente**
- **Como executar (Docker)**
- **Como executar local (Maven)**
- **Construir imagem Docker**
- **Testes**
- **Endpoints principais**
- **Estrutura do projeto**
- **Boas práticas e segurança**
- **Contribuição**
- **Contato / Licença**

---

## Visão Geral

Este projeto implementa uma API simples de autenticação e gestão de usuários usando uma arquitetura limpa (clean architecture). A autenticação é feita via JWT (Bearer token). O objetivo é servir como base para aplicações que precisam de autenticação segura e separação clara entre camadas.

## Funcionalidades

- Registro / criação de usuário (via endpoint de usuários)
- Autenticação (login) que retorna JWT
- Endpoints protegidos por JWT
- Configuração para rodar com PostgreSQL via Docker Compose
- Testes configurados com H2 para execução rápida

## Stack Tecnológica

- Java 17
- Spring Boot
- Maven (com wrapper `mvnw` / `mvnw.cmd`)
- PostgreSQL (via Docker Compose)
- JWT (implementado em `security/`)

## Requisitos

- Java 17 instalado
- Maven (opcional se usar `./mvnw`)
- Docker e Docker Compose (recomendado para ambiente de desenvolvimento)

---

## Variáveis de ambiente

Copie o arquivo `.env.exemple` para `.env` na raiz do projeto e preencha os valores reais (não comite o `.env`).

Exemplo (`.env`):

```dotenv
# Copy to .env and fill real secrets (do NOT commit .env)
DB_USER=user
DB_PASS=password
DB_NAME=database_name
JWT_SECRET=change-me-to-a-secure-secret
APP_PORT=8080

# Optional: change Postgres image / version
POSTGRES_IMAGE=postgres:15
```

As variáveis são consumidas por `src/main/resources/application.yml`.

---

## Como executar (Docker Compose) — recomendado

1. Copie `.env.exemple` para `.env` e atualize os valores.
2. Suba os serviços (aplicação + banco):

```powershell
docker compose up --build -d
```

3. A aplicação estará disponível na porta configurada (`APP_PORT`, padrão `8080`).

Para parar e remover containers:

```powershell
docker compose down
```

---

## Como executar local (Maven)

1. Configure variáveis de ambiente (ou um `application.yml` local) apontando para um banco Postgres acessível.
2. Build e execução:

```powershell
# Build
./mvnw clean package

# Rodar
./mvnw spring-boot:run
```

Se preferir, execute o jar gerado:

```powershell
java -jar target/springboot-clean-jwt-postgres-0.0.1-SNAPSHOT.jar
```

---

## Construir imagem Docker

Para gerar apenas a imagem Docker via Dockerfile:

```powershell
docker build -t homefinancasserver:latest .
```

---

## Testes

Os testes de integração/unitários estão sob `src/test/java` e usam H2 (configuração em `src/test/resources/application-test.yml`). Para executar os testes:

```powershell
./mvnw test
```

Relatórios de testes ficam em `target/surefire-reports`.

---

## Endpoints principais

Base path: `/api`

- Autenticação
   - `POST /api/auth/login` — Recebe um `AuthRequest` e retorna `AuthResponse` com token JWT.
      - Exemplo de `AuthRequest` (JSON):
         ```json
         { "username": "usuario", "password": "senha" }
         ```
      - Resposta (exemplo):
         ```json
         { "token": "<JWT>", "username": "usuario", "email": "user@example.com" }
         ```

- Usuários
   - `GET /api/users` — Lista todos os usuários (requer JWT Bearer).
   - `GET /api/users/{id}` — Busca usuário por ID (requer JWT Bearer).
   - `POST /api/users` — Cria um novo usuário (JSON no corpo).

Observação: A proteção via JWT é implementada na camada de segurança (`security/`). Inclua o header `Authorization: Bearer <token>` nas requisições a endpoints protegidos.

Exemplo rápido com `curl` (PowerShell ajustado):

```powershell
# Autenticar e obter token
$resp = curl -Method POST -Uri http://localhost:8080/api/auth/login -Body (@{username='usuario';password='senha'} | ConvertTo-Json) -ContentType 'application/json'
$token = ($resp | ConvertFrom-Json).token

# Usar token para listar usuários
curl -Method GET -Uri http://localhost:8080/api/users -Headers @{ Authorization = "Bearer $token" }
```

---

## Estrutura do projeto (resumida)

- `src/main/java/com/example/cleanapp` — código fonte principal
   - `adapters/inbound/web/controller` — controladores REST (`AuthController`, `UserController`)
   - `adapters/outbound/persistence` — entidades, mapeadores e repositórios
   - `application` — casos de uso (`service`, `port`, `dto`)
   - `domain` — modelos de domínio
   - `security` — filtros, provider e configuração JWT
- `src/main/resources/application.yml` — configurações de runtime
- `src/test` — testes unitários e de integração

---

## Boas práticas / Segurança

- Nunca comite o arquivo `.env` com segredos reais.
- Use um `JWT_SECRET` forte e rotacione conforme política da sua organização.
- Em produção, ative HTTPS e proteja o tráfego entre a aplicação e o banco.
- Nunca exponha tokens JWT em URLs ou logs.

---

## Como contribuir

1. Fork do repositório
2. Criar branch com a feature/bugfix
3. Abrir PR descrevendo mudanças

---

## Contato / Licença

Projeto disponibilizado como exemplo. Veja o arquivo `pom.xml` para dependências e versão.

Licença: MIT (ver `LICENSE` se presente)

---

Arquivo criado/atualizado: `README.md`.
