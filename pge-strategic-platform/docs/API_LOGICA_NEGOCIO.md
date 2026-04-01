# Lógica de Negócio e APIs - PGE Strategic Platform

## Visão Geral

Este documento descreve a lógica de negócio e as APIs REST implementadas para o módulo de Planejamento Estratégico da Plataforma PGE.

## Arquitetura em Camadas

```
Controller (REST API) → Service (Regras de Negócio) → Repository (Acesso a Dados) → Entity (JPA)
                              ↓
                          DTO (Data Transfer Objects)
```

## APIs Implementadas

### 1. Planos Estratégicos
**Endpoint:** `/api/planning/strategic-plans`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Lista todos os planos estratégicos |
| GET | `/{id}` | Obtém um plano específico |
| POST | `/` | Cria novo plano estratégico |
| PUT | `/{id}` | Atualiza plano existente |
| DELETE | `/{id}` | Remove plano estratégico |

**Regras de Negócio:**
- Um plano deve ter data de início e fim válidas
- Apenas um plano pode estar ativo por vez (configurável)
- Plano não pode ser excluído se possuir objetivos vinculados

---

### 2. Análise SWOT
**Endpoint:** `/api/planning/swot-analysis`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/plan/{planId}` | Lista análises SWOT de um plano |
| GET | `/{id}` | Obtém análise SWOT específica |
| POST | `/` | Cria nova análise SWOT |
| PUT | `/{id}` | Atualiza análise SWOT |
| DELETE | `/{id}` | Remove análise SWOT |

**Regras de Negócio:**
- Cada plano pode ter múltiplas análises SWOT
- Itens (forças, fraquezas, oportunidades, ameaças) são ordenáveis
- Validação de consistência entre itens internos e externos

---

### 3. Indicadores (KPIs)
**Endpoint:** `/api/planning/kpis`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/objective/{objectiveId}` | Lista KPIs de um objetivo |
| GET | `/{id}` | Obtém KPI específico |
| POST | `/` | Cria novo KPI |
| PUT | `/{id}` | Atualiza KPI |
| DELETE | `/{id}` | Remove KPI |

**Regras de Negócio:**
- KPI deve estar vinculado a um objetivo estratégico
- Fórmula de cálculo é obrigatória
- Valores mínimo, máximo e alvo devem ser consistentes
- Frequência de medição define próxima data automática

---

### 4. Medições de KPI
**Endpoint:** `/api/planning/kpi-measurements`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/kpi/{kpiId}` | Lista medições de um KPI (ordenado por data) |
| GET | `/{id}` | Obtém medição específica |
| POST | `/` | Registra nova medição |
| DELETE | `/{id}` | Remove medição |

**Regras de Negócio:**
- Ao registrar medição, atualiza próxima data do KPI
- Gera alertas automáticos:
  - ✅ Meta atingida (valor ≥ target)
  - ⚠️ Abaixo do mínimo (valor < minValue)
  - 🔴 Acima do máximo (valor > maxValue)
- Evidências podem ser anexadas (URLs de documentos)

---

### 5. Planos de Ação
**Endpoint:** `/api/planning/action-plans`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/objective/{objectiveId}` | Lista planos de ação de um objetivo |
| GET | `/{id}` | Obtém plano de ação específico |
| POST | `/` | Cria novo plano de ação |
| PUT | `/{id}` | Atualiza plano de ação |
| DELETE | `/{id}` | Remove plano de ação |

**Regras de Negócio:**
- Plano de ação deve estar vinculado a um objetivo
- Tarefas têm porcentagem de conclusão individual
- Orçamento é opcional mas recomendado
- Status é calculado baseado nas tarefas (Não Iniciado, Em Andamento, Concluído)

---

## DTOs (Data Transfer Objects)

Todos os DTOs estão no pacote `com.pge.strategic.dto.planning`:

| DTO | Descrição |
|-----|-----------|
| `StrategicPlanDTO` | Plano estratégico completo |
| `SwotAnalysisDTO` | Análise SWOT com listas de itens |
| `StringItemDTO` | Item de texto ordenável (SWOT) |
| `ObjectiveDTO` | Objetivo estratégico |
| `KpiDTO` | Indicador chave de desempenho |
| `KpiMeasurementDTO` | Medição periódica de KPI |
| `ActionPlanDTO` | Plano de ação com tarefas |
| `ActionTaskDTO` | Tarefa individual de plano de ação |
| `InstitutionalIdentityDTO` | Identidade institucional (missão, visão, valores) |

---

## Serviços Implementados

| Serviço | Pacote | Responsabilidade |
|---------|--------|------------------|
| `StrategicPlanService` | `service.planning` | CRUD de planos estratégicos |
| `SwotAnalysisService` | `service.planning` | Gestão de análises SWOT |
| `KpiService` | `service.planning` | CRUD de indicadores |
| `KpiMeasurementService` | `service.planning` | Registro de medições com alertas |
| `ActionPlanService` | `service.planning` | Gestão de planos de ação |

---

## Controllers REST

| Controller | Endpoint Base | CORS |
|------------|---------------|------|
| `StrategicPlanController` | `/api/planning/strategic-plans` | Habilitado (*) |
| `KpiController` | `/api/planning/kpis` | Habilitado (*) |
| `KpiMeasurementController` | `/api/planning/kpi-measurements` | Habilitado (*) |
| `ActionPlanController` | `/api/planning/action-plans` | Habilitado (*) |

---

## Transacionalidade

Todos os serviços utilizam `@Transactional` do Spring:
- **Leitura:** `@Transactional(readOnly = true)` para consultas
- **Escrita:** Transações completas para create/update/delete
- **Rollback automático** em caso de exceções

---

## Validações de Negócio

### Plano Estratégico
```java
// Data de fim deve ser posterior à data de início
if (endDate.isBefore(startDate)) {
    throw new BusinessException("Data de fim deve ser posterior à data de início");
}
```

### KPI
```java
// Valores devem seguir ordem: min <= target <= max
if (minValue.compareTo(targetValue) > 0 || targetValue.compareTo(maxValue) > 0) {
    throw new BusinessException("Valores inconsistentes: min <= target <= max");
}
```

### Medição de KPI
```java
// Alerta se abaixo do mínimo
if (measuredValue.compareTo(kpi.getMinValue()) < 0) {
    alertService.createAlert("KPI abaixo do mínimo", kpi);
}
```

---

## Próximas Implementações

1. **Objetivos Estratégicos** - CRUD completo com dependências
2. **OKRs** - Objetivos com Resultados-Chave
3. **Mapa Estratégico** - Elementos visuais e conexões
4. **BSC Perspectives** - Perspectivas do Balanced Scorecard
5. **Eixos Estratégicos** - Agrupamentos temáticos
6. **Documentos Finais** - Geração de relatórios para impressão
7. **Identidade Institucional** - Missão, visão, valores

---

## Segurança (A Implementar)

- JWT Authentication
- Autorização baseada em papéis (Admin, Gestor, Colaborador)
- Auditoria de operações (quem fez o quê e quando)
- Permissões granulares por módulo

---

## Documentação OpenAPI/Swagger

A documentação completa das APIs será gerada automaticamente via Springdoc OpenAPI:
- URL: `http://localhost:8080/forpdi/swagger-ui.html`
- JSON: `http://localhost:8080/forpdi/v3/api-docs`
