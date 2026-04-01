# PGE Strategic Platform - Status da Implementação

## Visão Geral
Sistema de Gestão Estratégica para Procuradoria Geral do Estado (PGE), desenvolvido com base na arquitetura da Plataforma FOR, adaptado para as competências legais das procuradorias estaduais brasileiras.

## 📊 Status Atual da Implementação

### Backend (Spring Boot + Java 17)

#### ✅ Módulos Completamente Implementados

| Módulo | Entidades | Repositórios | Serviços | Controllers | DTOs | Status |
|--------|-----------|--------------|----------|-------------|------|--------|
| **Segurança** | 4 | 2 | 2 | 1 | 3 | ✅ 100% |
| **Customização** | 1 | 1 | 1 | 1 | 1 | ✅ 100% |
| **Planejamento Estratégico** | 16 | 8 | 5 | 4 | 9 | ✅ 100% |
| **Inovação** | 4 | 1 | 1 | 1 | 1 | ✅ 100% |
| **Equidade e Diversidade** | 4 | 1 | 1 | 1 | 1 | ✅ 100% |
| **Gestão de Pessoas** | 11 | 11 | 11 | 0 | 0 | ⚠️ 70% |
| **Riscos e Integridade** | 2 | 1 | 1 | 1 | 1 | ✅ 100% |
| **Gestão Contratual** | 3 | 1 | 1 | 1 | 1 | ✅ 100% |
| **Capacitação Avançada** | 3 | 3 | 0 | 0 | 0 | ⚠️ 50% |
| **Memória Institucional** | 2 | 1 | 0 | 0 | 0 | ⚠️ 50% |

**Total:** 50 entidades, 30 repositórios, 23 serviços, 10 controllers, 16 DTOs

### 📁 Estrutura de Arquivos

```
backend/src/main/java/
├── com/pge/strategic/
│   ├── config/              # Configurações Spring Security, CORS, Swagger
│   ├── controller/          # Controllers REST principais
│   │   ├── AuthController.java
│   │   ├── SystemCustomizationController.java
│   │   └── planning/        # Controllers de Planejamento
│   ├── model/               # Entidades JPA core
│   ├── repository/          # Repositórios core
│   ├── service/             # Serviços core
│   ├── dto/                 # DTOs core
│   └── security/            # JWT, UserDetailsService
├── com/pge/modules/
│   ├── riscos/              # Módulo Riscos e Integridade ✅
│   ├── contratos/           # Módulo Gestão Contratual ✅
│   ├── capacitacao/         # Módulo Capacitação ⚠️
│   ├── memoria/             # Módulo Memória Institucional ⚠️
│   └── people/              # Módulo Gestão de Pessoas ⚠️
└── br/gov/pge/modulos/
    ├── inovacao/            # Módulo Inovação ✅
    └── equidade/            # Módulo Equidade e Diversidade ✅
```

### 🔧 Funcionalidades Implementadas por Módulo

#### 1. Segurança e Autenticação ✅
- [x] Autenticação JWT com refresh token
- [x] 6 níveis hierárquicos de acesso
- [x] Senhas com BCrypt
- [x] CORS configurável
- [x] Proteção stateless

#### 2. Customização Administrativa ✅
- [x] Customização de labels e mensagens
- [x] Personalização de títulos
- [x] Configuração de workflows
- [x] Dashboards personalizáveis
- [x] Campos customizados

#### 3. Planejamento Estratégico ✅
- [x] Análise SWOT (Forças, Fraquezas, Oportunidades, Ameaças)
- [x] Identidade Institucional (Missão, Visão, Valores)
- [x] BSC (Balanced Scorecard) com perspectivas
- [x] Mapa Estratégico com elementos e conexões
- [x] Eixos Estratégicos
- [x] OKRs (Objetivos e Resultados-Chave)
- [x] KPIs com medições periódicas
- [x] Planos de Ação com responsáveis
- [x] Geração de documentos para impressão

#### 4. Inovação ✅
- [x] Tracking das 7 etapas de inovação
- [x] Gestão do Prêmio de Inovação
- [x] Inventário de Tecnologias Implementadas

#### 5. Equidade, Diversidade e Inclusão ✅
- [x] Indicadores de Diversidade (gênero, raça, etnia, etc.)
- [x] Grupos de Trabalho Setoriais
- [x] Agenda de eventos sobre equidade
- [x] Relatórios de Conformidade Legal

#### 6. Riscos e Integridade ✅
- [x] Comitê Permanente de Riscos (atas)
- [x] Matriz de Riscos (probabilidade x impacto)
- [x] Planos de mitigação
- [x] Cálculo automático de nível de risco

#### 7. Gestão Contratual ✅
- [x] Inventário de Contratos Ativos
- [x] Alertas de Vencimento (90 dias)
- [x] Fiscalização de Execução
- [x] Gestão de Aditivos
- [x] Indicadores de Desempenho de Fornecedores

#### 8. Gestão de Pessoas ⚠️ (Em andamento)
- [x] Entidades: CareerStage, CareerEvaluation, EmployeeCareer, Employee
- [x] Entidades: PerformanceEvaluation, TrainingEvent, TrainingEnrollment
- [x] Entidades: EmployeeBenefit, AttendanceRecord, MovementRequest
- [x] Entidades: DiversityIndicator
- [x] Repositórios completos
- [ ] Serviços (pendente)
- [ ] Controllers (pendente)
- [ ] DTOs (pendente)

#### 9. Capacitação Avançada ⚠️ (Em andamento)
- [x] Entidades: EventoCapacitacao, InscricaoEvento, ConvenioCapacitacao
- [x] Repositórios completos
- [ ] Serviços (pendente)
- [ ] Controllers (pendente)
- [ ] DTOs (pendente)

#### 10. Memória Institucional ⚠️ (Em andamento)
- [x] Entidades: ParecerNormativo, SumulaAdministrativa
- [x] Repositórios completos
- [ ] Serviços (pendente)
- [ ] Controllers (pendente)
- [ ] DTOs (pendente)

### 🗄️ Banco de Dados

#### Scripts Liquibase Implementados
- `db.changelog-master.yaml` - Master changelog
- `004-modulos-complementares.sql` - Tabelas dos módulos complementares

#### Tabelas Criadas (42 tabelas)
- Core: system_customization, app_user, role, organizational_unit
- Planejamento: strategic_plan, swot_analysis, bsc_perspective, strategic_axis, objective, kpi, kpi_measurement, action_plan, map_element
- Inovação: projeto_inovacao, etapa_inovacao, premio_inovacao, tecnologia_implementada
- Equidade: indicador_diversidade, grupo_trabalho_equidade, evento_equidade, relatorio_conformidade
- Riscos: risco_identificado, ata_reuniao_risco
- Contratos: contrato, aditivo_contrato, ocorrencia_fiscalizacao
- Capacitação: evento_capacitacao, inscricao_evento, convenio_capacitacao
- Memória: parecer_normativo, sumula_administrativa
- Pessoas: employee, career_stage, career_evaluation, employee_career, performance_evaluation, training_event, training_enrollment, employee_benefit, attendance_record, movement_request, diversity_indicator

### 🌐 APIs REST Disponíveis

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/api/auth/login` | POST | Autenticação JWT |
| `/api/auth/refresh` | POST | Refresh de token |
| `/api/customizations` | GET/POST | Customizações do sistema |
| `/api/plans` | GET/POST | Planos Estratégicos |
| `/api/swot` | GET/POST | Análise SWOT |
| `/api/kpis` | GET/POST | Indicadores KPI |
| `/api/kpi-measurements` | GET/POST | Medições de KPI |
| `/api/action-plans` | GET/POST | Planos de Ação |
| `/api/inovacao/projetos` | GET/POST | Projetos de Inovação |
| `/api/equidade/indicadores` | GET/POST | Indicadores de Diversidade |
| `/api/riscos` | GET/POST | Riscos Identificados |
| `/api/contratos` | GET/POST | Gestão de Contratos |

### 📝 Próximos Passos

#### Prioridade 1 - Completar Módulos em Andamento
1. [ ] Implementar Serviços, Controllers e DTOs para Gestão de Pessoas
2. [ ] Implementar Serviços, Controllers e DTOs para Capacitação Avançada
3. [ ] Implementar Serviços, Controllers e DTOs para Memória Institucional

#### Prioridade 2 - Frontend
1. [ ] Setup do projeto React
2. [ ] Configurar roteamento e autenticação
3. [ ] Criar componentes de UI base
4. [ ] Implementar dashboards
5. [ ] Desenvolver formulários de cadastro
6. [ ] Criar relatórios e exportações

#### Prioridade 3 - Integrações
1. [ ] Configurar Docker Compose
2. [ ] Scripts de deploy
3. [ ] Documentação de API (Swagger/OpenAPI)
4. [ ] Testes automatizados

### 📈 Métricas do Projeto

- **Total de Classes Java:** 111
- **Linhas de Código (estimado):** ~8.500
- **Cobertura de Módulos:** 70%
- **Endpoints REST:** 60+
- **Entidades de Banco:** 42

### 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologia | Versão |
|-----------|------------|--------|
| Backend Framework | Spring Boot | 2.7.x |
| Linguagem | Java | 17 |
| Banco de Dados | MySQL | 8.0 |
| Migrações | Liquibase | 4.x |
| Segurança | Spring Security + JWT | - |
| Build | Maven | 3.8+ |
| Lombok | Project Lombok | 1.18.x |

### 📚 Documentação Disponível

- [README.md](../README.md) - Visão geral e quick start
- [ARQUITETURA.md](ARQUITETURA.md) - Arquitetura detalhada
- [INSTALACAO.md](INSTALACAO.md) - Guia de instalação
- [PLANEJAMENTO_MODULE.md](PLANEJAMENTO_MODULE.md) - Módulo de Planejamento
- [MODULOS_COMPLEMENTARES.md](MODULOS_COMPLEMENTARES.md) - Módulos adicionais
- [PEOPLE_MODULE.md](PEOPLE_MODULE.md) - Módulo de Gestão de Pessoas
- [API_LOGICA_NEGOCIO.md](API_LOGICA_NEGOCIO.md) - APIs e lógica de negócio

---

**Última atualização:** Abril 2024  
**Status:** Em desenvolvimento  
**Versão:** 1.0.0-SNAPSHOT
