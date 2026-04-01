# 🐳 Guia de Instalação e Deploy com Docker

## PGE Strategic Platform - Sistema de Gestão Estratégica

Este documento descreve o processo completo de instalação e deploy do sistema utilizando Docker Compose.

---

## 📋 Pré-requisitos

- **Docker** versão 20.10 ou superior
- **Docker Compose** versão 2.0 ou superior
- **Git** para clonar o repositório
- **8GB RAM** mínimo recomendado
- **20GB** de espaço em disco

---

## 🚀 Instalação Rápida

### 1. Clonar Repositório

```bash
git clone https://github.com/your-org/pge-strategic-platform.git
cd pge-strategic-platform/docker
```

### 2. Configurar Variáveis de Ambiente

```bash
# Copiar arquivo de exemplo
cp .env.example .env

# Editar com suas configurações (obrigatório alterar senhas!)
nano .env
```

**⚠️ IMPORTANTE:** Altere todas as senhas padrão antes de usar em produção!

### 3. Iniciar os Serviços

```bash
# Modo desenvolvimento (apenas database, backend e frontend)
docker-compose up -d

# Modo produção (inclui Nginx como reverse proxy)
docker-compose --profile production up -d
```

### 4. Verificar Status

```bash
# Ver logs em tempo real
docker-compose logs -f

# Ver status dos containers
docker-compose ps

# Verificar saúde dos serviços
curl http://localhost:8080/forpdi/actuator/health
```

---

## 🔧 Configuração Detalhada

### Variáveis de Ambiente Principais

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `DB_PASSWORD` | Senha do banco de dados | `pge_secure_2025_change_in_prod` |
| `JWT_SECRET` | Chave secreta JWT | `pge_jwt_super_secret_key...` |
| `SMTP_HOST` | Servidor SMTP | `smtp.example.com` |
| `AWS_S3_BUCKET` | Bucket S3 para documentos | `pge-documents-bucket` |
| `RECAPTCHA_SITE_KEY` | Chave reCAPTCHA | (obter no Google) |
| `BACKEND_PORT` | Porta do backend | `8080` |
| `FRONTEND_PORT` | Porta do frontend | `80` |

### Estrutura de Diretórios

```
docker/
├── docker-compose.yml          # Orquestração dos containers
├── .env.example                # Template de variáveis de ambiente
├── .env                        # Configurações locais (não versionar)
├── database/
│   └── init/
│       └── 01-init-database.sql
├── nginx/
│   ├── nginx.conf
│   ├── conf.d/
│   │   └── default.conf
│   └── ssl/                    # Certificados SSL (produção)
└── README.md
```

---

## 📦 Comandos Úteis

### Gerenciamento de Containers

```bash
# Iniciar todos os serviços
docker-compose up -d

# Parar todos os serviços
docker-compose down

# Reiniciar um serviço específico
docker-compose restart backend

# Reconstruir imagens
docker-compose build --no-cache

# Remover volumes (cuidado: apaga dados!)
docker-compose down -v
```

### Logs e Monitoramento

```bash
# Logs de todos os serviços
docker-compose logs

# Logs de um serviço específico
docker-compose logs backend

# Acompanhar logs em tempo real
docker-compose logs -f backend

# Estatísticas de uso de recursos
docker stats
```

### Acesso aos Containers

```bash
# Acessar shell do backend
docker-compose exec backend sh

# Acessar shell do banco de dados
docker-compose exec database mysql -u pge_user -p pge_strategic

# Acessar shell do frontend
docker-compose exec frontend sh
```

---

## 🔒 Segurança

### Produção

1. **Alterar todas as senhas padrão** no arquivo `.env`
2. **Gerar nova chave JWT** com pelo menos 256 bits:
   ```bash
   openssl rand -base64 32
   ```
3. **Configurar HTTPS** com certificados válidos
4. **Restringir acesso** ao banco de dados (não expor porta 3306)
5. **Usar Docker secrets** para informações sensíveis
6. **Habilitar perfil production** do Spring Boot

### Backup do Banco de Dados

```bash
# Criar backup
docker-compose exec database mysqldump -u pge_user -p pge_strategic > backup_$(date +%Y%m%d_%H%M%S).sql

# Restaurar backup
cat backup_YYYYMMDD_HHMMSS.sql | docker-compose exec -T database mysql -u pge_user -p pge_strategic
```

---

## 🏗️ Arquitetura

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│   Nginx     │────▶│   Frontend   │────▶│   Backend   │
│  (Proxy)    │     │   (React)    │     │ (Spring Boot)│
│  :80 / :443 │     │     :80      │     │    :8080    │
└─────────────┘     └──────────────┘     └──────┬──────┘
                                                 │
                                                 ▼
                                        ┌──────────────┐
                                        │   Database   │
                                        │   (MySQL)    │
                                        │     :3306    │
                                        └──────────────┘
```

### Serviços

| Serviço | Imagem | Porta | Descrição |
|---------|--------|-------|-----------|
| `database` | `mysql:8.0` | 3306 | Banco de dados MySQL |
| `backend` | Custom (Spring Boot) | 8080 | API REST Java |
| `frontend` | Custom (React + Apache) | 80/443 | Interface web |
| `nginx-proxy` | `nginx:alpine` | 8000/8443 | Reverse proxy (prod) |

---

## 🐛 Troubleshooting

### Backend não inicia

```bash
# Verificar logs
docker-compose logs backend

# Verificar conexão com banco
docker-compose exec backend curl http://database:3306
```

### Erro de permissão no MySQL

```bash
# Resetar permissões
docker-compose exec database mysql -u root -p -e "FLUSH PRIVILEGES;"
```

### Frontend não carrega

```bash
# Verificar build
docker-compose build frontend

# Limpar cache do navegador
Ctrl+Shift+R (Chrome/Firefox)
```

### Espaço em disco insuficiente

```bash
# Limpar imagens não utilizadas
docker system prune -a

# Remover volumes órfãos
docker volume prune
```

---

## 📊 Monitoramento

### Health Checks

- **Backend**: `http://localhost:8080/forpdi/actuator/health`
- **Frontend**: `http://localhost/`
- **Database**: `docker-compose exec database mysqladmin ping -h localhost`

### Métricas Recomendadas

- CPU e Memória por container
- Tempo de resposta das APIs
- Conexões ativas no banco de dados
- Espaço em disco dos volumes

---

## 🔄 Atualização do Sistema

```bash
# 1. Parar serviços
docker-compose down

# 2. Atualizar código
git pull origin main

# 3. Reconstruir imagens
docker-compose build --no-cache

# 4. Iniciar serviços
docker-compose up -d

# 5. Verificar migrações do banco
docker-compose logs backend | grep "Liquibase"
```

---

## 📞 Suporte

Em caso de dúvidas ou problemas:

- **Documentação**: `/workspace/pge-strategic-platform/docs/`
- **Logs**: `docker-compose logs -f`
- **Issues**: Abrir issue no repositório GitHub

---

## ✅ Checklist de Implantação

- [ ] Copiar `.env.example` para `.env`
- [ ] Alterar todas as senhas padrão
- [ ] Gerar nova chave JWT
- [ ] Configurar SMTP para envio de emails
- [ ] Configurar AWS S3 (opcional)
- [ ] Configurar reCAPTCHA
- [ ] Testar health checks de todos os serviços
- [ ] Realizar backup inicial do banco
- [ ] Documentar credenciais de acesso
- [ ] Treinar equipe administrativa

---

**Versão do Documento:** 1.0.0  
**Última Atualização:** 2025  
**Responsável:** Equipe de TI - PGE/MA
