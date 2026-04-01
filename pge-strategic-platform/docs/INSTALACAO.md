# PGE Strategic Platform - Guia de Instalação e Deploy

## Visão Geral

Este documento descreve os passos para instalar e executar a **PGE Strategic Platform**, sistema de gestão estratégica para Procuradoria Geral do Estado.

## Pré-requisitos

### Desenvolvimento
- Java 17 ou superior
- Maven 3.8+
- MySQL 5.7/8.0 ou Docker
- Node.js 16+ (para frontend)
- Git

### Produção
- Docker e Docker Compose
- Servidor com mínimo 4GB RAM
- Storage persistente para banco de dados

## Estrutura do Projeto

```
pge-strategic-platform/
├── backend/                 # Spring Boot API
│   ├── src/main/java/      # Código fonte Java
│   ├── src/main/resources/ # Configurações
│   └── pom.xml             # Dependências Maven
├── frontend/               # React Application
│   ├── src/               # Código fonte React
│   └── package.json       # Dependências NPM
├── docker/                # Configurações Docker
└── docs/                  # Documentação
```

## Instalação Local (Desenvolvimento)

### 1. Clonar Repositório

```bash
git clone <repository-url> pge-strategic-platform
cd pge-strategic-platform
```

### 2. Configurar Banco de Dados

#### Opção A: Usando Docker (Recomendado)

```bash
docker run -d \
  --name pge-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=pge_strategic \
  -p 3306:3306 \
  mysql:8.0
```

#### Opção B: MySQL Local

Crie o banco de dados manualmente:

```sql
CREATE DATABASE pge_strategic 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

CREATE USER 'pge_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON pge_strategic.* TO 'pge_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar Variáveis de Ambiente

Crie um arquivo `.env` na raiz do backend:

```bash
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=pge_strategic
DB_USERNAME=root
DB_PASSWORD=rootpassword

# Server
SERVER_PORT=8080

# JWT
JWT_SECRET=your-super-secret-key-min-32-characters-long-change-in-production
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000

# Allowed Origins
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:80
```

### 4. Build e Executar Backend

```bash
cd backend

# Compilar projeto
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080/pge`
Swagger UI: `http://localhost:8080/pge/swagger-ui.html`

### 5. Build e Executar Frontend

```bash
cd frontend

# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm start
```

O frontend estará disponível em: `http://localhost:3000`

## Deploy com Docker Compose

### 1. Criar Arquivo docker-compose.yml

Na raiz do projeto:

```yaml
version: '3.8'

services:
  database:
    image: mysql:8.0
    container_name: pge-database
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME}
    volumes:
      - pge_mysql_data:/var/lib/mysql
    ports:
      - "3306:3306"
    networks:
      - pge-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  backend:
    build: ./backend
    container_name: pge-backend
    environment:
      DB_HOST: database
      DB_PORT: 3306
      DB_NAME: ${DB_NAME}
      DB_USERNAME: root
      DB_PASSWORD: ${DB_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
    ports:
      - "8080:8080"
    depends_on:
      database:
        condition: service_healthy
    networks:
      - pge-network

  frontend:
    build: ./frontend
    container_name: pge-frontend
    ports:
      - "80:80"
    depends_on:
      - backend
    networks:
      - pge-network

volumes:
  pge_mysql_data:

networks:
  pge-network:
    driver: bridge
```

### 2. Executar com Docker Compose

```bash
# Build e start de todos os serviços
docker-compose up -d --build

# Ver logs
docker-compose logs -f

# Parar serviços
docker-compose down
```

## Acesso Inicial

### Usuário Administrador Padrão

Após a primeira execução, crie o usuário administrador:

**Via API:**
```bash
curl -X POST http://localhost:8080/pge/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@pge.gov.br",
    "password": "Admin@123456",
    "fullName": "Administrador do Sistema",
    "roleLevel": "ADMIN"
  }'
```

**Ou via banco de dados:**
```sql
-- A senha é hash bcrypt de "Admin@123456"
INSERT INTO app_user (email, password_hash, full_name, role_level, is_active, created_at)
VALUES (
  'admin@pge.gov.br',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
  'Administrador do Sistema',
  'ADMIN',
  true,
  NOW()
);
```

## Customização do Sistema

### Acessar Interface de Customização

1. Login como administrador
2. Navegue para: **Administração → Customizações do Sistema**
3. Edite labels, mensagens e configurações

### Exemplos de Customização

```json
{
  "customizationKey": "SYSTEM_TITLE",
  "customizationValue": "PGE/SP - Sistema de Gestão Estratégica",
  "category": "TITLES",
  "description": "Título principal exibido no header"
}
```

```json
{
  "customizationKey": "LABEL_PROCESSO_JUDICIAL",
  "customizationValue": "Ação Judicial",
  "category": "LABELS",
  "description": "Label alternativo para processo judicial"
}
```

## Troubleshooting

### Problemas Comuns

#### 1. Erro de Conexão com Banco de Dados

```
Cannot create PoolableConnectionFactory
```

**Solução:**
- Verifique se o MySQL está rodando
- Confirme credenciais no application.yml
- Teste conexão: `mysql -u root -p -h localhost`

#### 2. Porta 8080 já em uso

```
Port 8080 was already in use
```

**Solução:**
```bash
# Altere a porta no application.yml
server.port=8081

# Ou mate o processo usando a porta
lsof -ti:8080 | xargs kill -9
```

#### 3. Liquibase Migration Failed

```
Migration failed for change set db.changelog-master.yaml::001
```

**Solução:**
```sql
-- Limpe as tabelas do Liquibase
DROP TABLE IF EXISTS DATABASECHANGELOGLOCK;
DROP TABLE IF EXISTS DATABASECHANGELOG;

-- Reinicie a aplicação
```

## Monitoramento

### Health Check

```bash
curl http://localhost:8080/pge/actuator/health
```

### Métricas

```bash
curl http://localhost:8080/pge/actuator/metrics
```

### Logs

```bash
# Backend logs
tail -f logs/pge-strategic-platform.log

# Docker logs
docker-compose logs -f backend
```

## Segurança em Produção

### Checklist de Segurança

- [ ] Alterar JWT_SECRET para valor único e seguro
- [ ] Alterar senha padrão do administrador
- [ ] Configurar HTTPS/SSL
- [ ] Restringir CORS para domínios específicos
- [ ] Habilitar autenticação multifator
- [ ] Configurar backup automático do banco
- [ ] Implementar rate limiting
- [ ] Auditar logs regularmente

## Suporte

Para dúvidas e suporte técnico:
- Documentação completa: `/docs`
- Swagger API: `/swagger-ui.html`
- E-mail: suporte.pge@estado.gov.br

---

**Versão**: 1.0.0  
**Última atualização**: 2024
