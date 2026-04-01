# Módulo de Planejamento Estratégico - PGE Strategic Platform

## Visão Geral

Este módulo implementa todas as funcionalidades necessárias para elaboração periódica e acompanhamento do **Planejamento Estratégico** da Procuradoria Geral do Estado, incluindo:

- ✅ Análise Interna e Externa (SWOT/FOFA)
- ✅ Identidade Institucional (Missão, Visão, Valores)
- ✅ Balanced Scorecard (BSC) com 4 perspectivas + customizáveis
- ✅ Mapa Estratégico interativo
- ✅ Eixos Estratégicos
- ✅ OKRs (Objectives and Key Results)
- ✅ KPIs (Indicadores Chave de Desempenho)
- ✅ Gestão completa de Objetivos, Indicadores, Metas e Planos de Ação
- ✅ Responsáveis por cada componente
- ✅ Geração de documento final para impressão/exportação

## Entidades Implementadas

### Núcleo do Planejamento
| Entidade | Descrição |
|----------|-----------|
| `StrategicPlan` | Planejamento estratégico completo (ciclo) |
| `SwotAnalysis` | Análise SWOT (Forças, Oportunidades, Fraquezas, Ameaças) |
| `StrategicAxis` | Eixos estratégicos do planejamento |
| `BscPerspective` | Perspectivas do BSC (Financeira, Cliente, Processos, Aprendizado) |

### Objetivos e Resultados
| Entidade | Descrição |
|----------|-----------|
| `StrategicObjective` | Objetivos estratégicos vinculados a eixos e BSC |
| `KeyResult` | Resultados-chave (OKR) mensuráveis |
| `Kpi` | Indicadores de desempenho com medições periódicas |
| `KpiMeasurement` | Histórico de medições dos KPIs |

### Execução e Acompanhamento
| Entidade | Descrição |
|----------|-----------|
| `ActionPlan` | Planos de ação para alcançar objetivos |
| `ActionTask` | Tarefas detalhadas dos planos de ação |
| `ActionPlanResource` | Recursos necessários (humanos, financeiros, materiais) |
| `ObjectiveDependency` | Dependências entre objetivos |

### Visualização e Documentação
| Entidade | Descrição |
|----------|-----------|
| `StrategicMapElement` | Elementos visuais do mapa estratégico |
| `MapConnection` | Conexões entre elementos do mapa |
| `FinalDocument` | Documentos consolidados para exportação |

## Customização pelo Administrador

O sistema permite **customização completa** através das seguintes capacidades:

### 1. Identidade Institucional
- Missão, Visão e Valores editáveis
- Slogan personalizável
- Cores e temas do mapa estratégico

### 2. Perspectivas BSC Customizáveis
- 4 perspectivas clássicas pré-definidas
- Possibilidade de criar perspectivas personalizadas (`CUSTOM`)
- Ordem de exibição configurável
- Notas de customização por perspectiva

### 3. Estrutura de Eixos Estratégicos
- Códigos e títulos editáveis
- Múltiplos eixos por planejamento
- Coordenadores responsáveis por eixo

### 4. Modelos de Documentos
- Templates personalizáveis para exportação
- Seções selecionáveis (SWOT, BSC, Mapas, KPIs, etc.)
- Introdução e conclusão customizáveis
- Formato A4/A3, retrato/paisagem

### 5. Fluxos de Aprovação
- Status configuráveis do planejamento
- Usuários aprovadores definidos pelo admin
- Datas de aprovação rastreáveis

## API Endpoints (Planejamento)

```
GET    /api/planning/plans                 - Listar planejamentos
POST   /api/planning/plans                 - Criar novo planejamento
GET    /api/planning/plans/{id}            - Obter planejamento detalhado
PUT    /api/planning/plans/{id}            - Atualizar planejamento
DELETE /api/planning/plans/{id}            - Excluir planejamento
POST   /api/planning/plans/{id}/approve    - Aprovar planejamento

GET    /api/planning/plans/{id}/swot       - Listar análise SWOT
POST   /api/planning/swot                  - Criar item SWOT

GET    /api/planning/plans/{id}/axes       - Listar eixos estratégicos
POST   /api/planning/axes                  - Criar eixo estratégico

GET    /api/planning/plans/{id}/bsc        - Listar perspectivas BSC
POST   /api/planning/bsc                   - Criar perspectiva BSC

GET    /api/planning/objectives            - Listar objetivos
POST   /api/planning/objectives            - Criar objetivo estratégico

GET    /api/planning/objectives/{id}/okrs  - Listar OKRs de um objetivo
POST   /api/planning/okrs                  - Criar Key Result

GET    /api/planning/kpis                  - Listar KPIs
POST   /api/planning/kpis                  - Criar KPI
POST   /api/planning/kpis/{id}/measure     - Registrar medição de KPI

GET    /api/planning/action-plans          - Listar planos de ação
POST   /api/planning/action-plans          - Criar plano de ação

GET    /api/planning/map/{planId}          - Obter mapa estratégico
PUT    /api/planning/map/elements          - Atualizar elementos do mapa

POST   /api/planning/documents/generate    - Gerar documento final
GET    /api/planning/documents/{id}/download - Baixar documento gerado
```

## Banco de Dados

As tabelas são criadas via Liquibase no arquivo:
`src/main/resources/db/changelog/002-planning-module.xml`

Total de **16 tabelas** relacionadas ao módulo de planejamento.

## Próximos Passos

1. ✅ Modelagem de entidades concluída
2. ✅ Scripts Liquibase criados
3. ⏳ Implementar Services e Controllers
4. ⏳ Criar DTOs e mapeadores
5. ⏳ Desenvolver frontend React
6. ⏳ Implementar geração de documentos (PDF)
7. ⏳ Criar dashboard de acompanhamento
