# 🚀 PGE Strategic Platform - Instruções de Instalação

## ✅ Pré-requisitos

- **Docker** 20.10+ ([Instalação](https://docs.docker.com/get-docker/))
- **Docker Compose** 2.0+ (já incluso no Docker Desktop)
- **Git** para clonar o repositório
- **8GB RAM** mínimo recomendado
- **20GB espaço** em disco livre

---

## 📥 1. Clonar Repositório

```bash
cd /workspace
git clone <url-do-repositorio> pge-strategic-platform
cd pge-strategic-platform
```

---

## ⚙️ 2. Configurar Ambiente

### 2.1. Copiar arquivo de exemplo

```bash
cp .env.example .env
```

### 2.2. Editar variáveis de ambiente

```bash
nano .env
```

**⚠️ IMPORTANTE PARA PRODUÇÃO:**
- Altere TODAS as senhas padrão
- Gere um JWT_SECRET forte:
  ```bash
  # Linux/Mac
  openssl rand -base64 64
  
  # Windows PowerShell
  powershell -c "[System.Convert]::ToBase64String((New-Object Security.Cryptography.RNGCryptoServiceProvider).GetBytes(64))"
  ```

### 2.3. Variáveis obrigatórias

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_ROOT_PASSWORD` | Senha root MySQL | `SenhaForte123!` |
| `DB_PASSWORD` | Senha usuário app | `SenhaForte123!` |
| `JWT_SECRET` | Chave secreta JWT | Mínimo 32 caracteres |

### 2.4. Variáveis opcionais

- **reCAPTCHA**: Para proteção de formulários
- **SMTP**: Para envio de e-mails
- **AWS S3**: Para armazenamento de arquivos

---

## 🏗️ 3. Build e Execução

### 3.1. Construir imagens Docker

```bash
docker-compose build
```

### 3.2. Iniciar serviços

```bash
# Iniciar tudo
docker-compose up -d

# Ou iniciar passo a passo (recomendado primeira vez)
docker-compose up -d database
sleep 10
docker-compose up -d backend
sleep 30
docker-compose up -d frontend
```

### 3.3. Verificar status

```bash
docker-compose ps
docker-compose logs -f
```

---

## 🌐 4. Acessar Sistema

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **Frontend** | http://localhost | admin@pge.ma.gov.br / 123456 |
| **API** | http://localhost:8080/api | - |
| **Swagger** | http://localhost:8080/swagger-ui.html | - |
| **MySQL** | localhost:3306 | pge_user / senha do .env |

---

## 🔧 5. Comandos Úteis

### Ver logs

```bash
# Todos os serviços
docker-compose logs -f

# Apenas backend
docker-compose logs -f backend

# Apenas banco
docker-compose logs -f database
```

### Reiniciar serviços

```bash
docker-compose restart
```

### Parar sistema

```bash
docker-compose down
```

### Parar e remover volumes (CUIDADO: apaga dados!)

```bash
docker-compose down -v
```

### Rebuild após alterações no código

```bash
docker-compose build --no-cache
docker-compose up -d
```

---

## 📊 6. Troubleshooting

### Backend não inicia

```bash
# Verificar logs
docker-compose logs backend

# Verificar se banco está pronto
docker-compose logs database
docker-compose exec database mysqladmin ping -h localhost -u root -p
```

### Erro de conexão com banco

- Aguarde 60-90 segundos para o Spring Boot inicializar
- Verifique se `DB_NAME`, `DB_USER`, `DB_PASSWORD` estão corretos no `.env`

### Frontend não carrega

```bash
# Verificar logs
docker-compose logs frontend

# Testar API manualmente
curl http://localhost:8080/api/health
```

### Reset completo

```bash
docker-compose down -v
rm -rf backend/target
rm -rf frontend/node_modules
docker-compose build --no-cache
docker-compose up -d
```

---

## 🔒 7. Segurança em Produção

### 7.1. HTTPS Obrigatório

Configure certificados SSL no Nginx:

```bash
# Usando Let's Encrypt
certbot --nginx -d seu-dominio.pge.ma.gov.br
```

### 7.2. Firewall

Libere apenas portas necessárias:
- 80 (HTTP) → Redirecionar para 443
- 443 (HTTPS)
- Bloquear 3306 e 8080 externamente

### 7.3. Backup Automático

```bash
#!/bin/bash
# backup.sh
DATE=$(date +%Y%m%d_%H%M%S)
docker-compose exec -T database mysqldump -u root -p$DB_ROOT_PASSWORD $DB_NAME > backup_$DATE.sql
gzip backup_$DATE.sql
# Enviar para S3 ou outro storage
```

### 7.4. Monitoramento

- Configure healthchecks
- Use Prometheus + Grafana
- Monitore logs com ELK Stack

---

## 📞 8. Suporte

Em caso de dúvidas ou problemas:

1. Verifique os logs: `docker-compose logs -f`
2. Consulte a documentação no diretório `/docs`
3. Abra uma issue no repositório

---

## 📋 Checklist de Implantação

- [ ] Copiou `.env.example` para `.env`
- [ ] Alterou todas as senhas padrão
- [ ] Gerou JWT_SECRET forte
- [ ] Configurou HTTPS (produção)
- [ ] Testou acesso ao frontend
- [ ] Testou login de administrador
- [ ] Configurou backup automático
- [ ] Documentou credenciais em local seguro

---

**✅ Sistema pronto para uso!**

Acesse http://localhost e faça login com:
- **Email:** admin@pge.ma.gov.br
- **Senha:** 123456

⚠️ **Altere a senha imediatamente após o primeiro acesso!**
