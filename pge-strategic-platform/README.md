# 🏛️ PGE Strategic Platform

## Sistema de Gestão Estratégica para Procuradoria Geral do Estado

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

---

## 📖 Sobre o Projeto

O **PGE Strategic Platform** é um sistema completo de gestão estratégico desenvolvido especificamente para Procuradorias Gerais do Estado, baseado na arquitetura da Plataforma FOR e adaptado para atender às competências legais das PGEs conforme leis e decretos estaduais.

### 🎯 Objetivos

- Centralizar a gestão do Planejamento Estratégico institucional
- Automatizar processos de acompanhamento de metas e indicadores
- Gerenciar programas de capacitação, inovação e diversidade
- Facilitar a conformidade com normas legais e regulamentares
- Prover dashboards executivos para tomada de decisão

---

## 🚀 Funcionalidades Principais

### 📊 Módulo de Planejamento Estratégico
- Análise SWOT, BSC, Mapa Estratégico, OKRs, KPIs
- Gestão completa de objetivos, indicadores, metas e planos de ação

### 👥 Módulo de Gestão de Pessoas
- Carreira, Capacitação (ESAP), Desempenho, Diversidade (CEDI)
- Inovação (INOVA-PGE), Benefícios, Frequência, Movimentação

### ⚠️ Módulo de Riscos e Integridade
- Comitê de Riscos, Matriz de Riscos, Plano de Integridade
- Auditorias, Correições, Indicadores de Conformidade

### 📝 Módulo de Gestão Contratual
- Inventário de Contratos, Alertas de Vencimento (90 dias)
- Fiscalização de Execução, Gestão de Aditivos

### 📚 Módulo de Memória Institucional
- Banco de Pareceres, Súmulas Administrativas
- Acervo Documental, Memória Institucional

### 🔐 Segurança e Customização
- JWT, 6 níveis hierárquicos, RBAC granular
- Customização completa de labels, workflows e dashboards

---

## 🏗️ Arquitetura Tecnológica

| Camada | Tecnologia |
|--------|------------|
| Backend | Java 17 + Spring Boot 2.7 |
| Frontend | React 18 + Apache HTTPD |
| Database | MySQL 8.0 + Liquibase |
| Infra | Docker + Docker Compose |
| Security | Spring Security + JWT |

---

## 📦 Status do Desenvolvimento

| Métrica | Quantidade |
|---------|------------|
| Classes Java | 125+ |
| Entidades JPA | 50 |
| Repositórios | 34 |
| Serviços | 30 |
| Controllers REST | 30 |
| DTOs | 29 |
| Cobertura Funcional | ~95% |

---

## 🚀 Instalação Rápida

```bash
# Clonar repositório
cd pge-strategic-platform

# Configurar ambiente
cp .env.example .env
nano .env  # Editar senhas e JWT_SECRET!

# Iniciar serviços (Docker Compose)
docker-compose up -d --build

# Verificar status
docker-compose ps

# Acessar sistema
# Frontend: http://localhost
# Backend API: http://localhost:8080/api
# Swagger: http://localhost:8080/swagger-ui.html
```

📖 **Documentação completa:** Veja abaixo para detalhes de instalação, troubleshooting e produção.

---

## 🔑 Acesso Inicial

| Usuário | Email | Senha | Permissões |
|---------|-------|-------|------------|
| Admin | admin@pge.ma.gov.br | 123456 | Acesso total ao sistema |
| Gestor | gestor@pge.ma.gov.br | 123456 | Gestão de módulos |
| Usuario | usuario@pge.ma.gov.br | 123456 | Operações básicas |

⚠️ **IMPORTANTE**: Alterar todas as senhas imediatamente após o primeiro acesso!

---

## 📚 Documentação Completa

### Guias de Instalação e Uso

- [Guia Docker e Deploy](#-guia-docker-e-deploy)
- [Arquitetura do Sistema](docs/ARQUITETURA.md)
- [Instalação Detalhada](docs/INSTALACAO.md)
- [Documentação da API](docs/API.md)

### Módulos do Sistema

| Módulo | Funcionalidades | Status |
|--------|----------------|--------|
| Planejamento Estratégico | SWOT, BSC, OKRs, KPIs, Mapas | ✅ Completo |
| Gestão de Pessoas | Carreira, Capacitação, Desempenho, DEI | ✅ Completo |
| Inovação | 7 etapas, Prêmio, Inventário Tech | ✅ Completo |
| Riscos e Integridade | Matriz, Plano, Auditorias | ✅ Completo |
| Gestão Contratual | Contratos, Alertas, Fiscalização | ✅ Completo |
| Memória Institucional | Pareceres, Súmulas, Acervo | ✅ Completo |

---

## 🔧 Troubleshooting

### Backend não inicia
```bash
# Verificar logs
docker-compose logs backend

# Verificar saúde do banco
docker-compose exec database mysqladmin ping -h localhost

# Validar conexão
curl http://localhost:8080/api/health
```

### Frontend não carrega
```bash
# Logs do Nginx
docker-compose logs frontend

# Testar backend
curl http://localhost:8080/api/health

# Limpar cache do navegador (Ctrl+Shift+R)
```

### Erro de autenticação JWT
```bash
# Verificar JWT_SECRET no .env
cat .env | grep JWT_SECRET

# Reiniciar backend
docker-compose restart backend
```

### Banco de dados não conecta
```bash
# Healthcheck
docker-compose ps database

# Acessar MySQL
docker-compose exec database mysql -u pge_user -p

# Verificar migrations
docker-compose logs backend | grep liquibase
```

---

## 📈 Produção

### Ajustes Recomendados

#### Variáveis de Ambiente (.env)
```ini
# Usar senhas fortes
DB_PASSWORD=<senha_forte_gerada>
JWT_SECRET=<chave_aleatoria_64_caracteres>

# Configurar SMTP real
SPRING_MAIL_HOST=smtp.pge.ma.gov.br
SPRING_MAIL_USERNAME=noreply@pge.ma.gov.br

# AWS S3 para arquivos
AWS_ACCESS_KEY_ID=<sua_key>
AWS_S3_BUCKET=pge-documentos-prod
```

#### Recursos Docker (docker-compose.yml)
```yaml
services:
  backend:
    deploy:
      resources:
        limits:
          cpus: '2.0'
          memory: 2G
```

### Backup Automatizado
```bash
# Script de backup diário
0 2 * * * docker-compose exec -T database mysqldump -u pge_user -p$DB_PASSWORD pge_db > /backup/pge_$(date +\%Y\%m\%d).sql
```

---

## 📄 Licença

Este projeto é distribuído sob licença **Apache 2.0**, sendo totalmente gratuito para uso, modificação e distribuição, inclusive para fins comerciais e governamentais.

**Todas as dependências são open source:**
- Java/Spring Boot: Apache 2.0
- React: MIT
- MySQL: GPL
- Nginx: BSD
- Docker: Apache 2.0

✅ **Adequado para órgãos públicos sem custos de licença.**

---

## 📞 Suporte e Contato

- **Equipe de Desenvolvimento**: dev@pge.ma.gov.br
- **Suporte Técnico**: suporte@pge.ma.gov.br
- **GitHub Issues**: https://github.com/pge-ma/pge-strategic-platform/issues

---

**Desenvolvido para a Procuradoria Geral do Estado do Maranhão - PGE/MA**  
© 2024-2025 - Plataforma Estratégica de Gestão

🏛️ **Tecnologia 100% Gratuita e Open Source**

