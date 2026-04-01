# Módulo de Gestão de Pessoas - PGE Strategic Platform

## Visão Geral

Módulo completo de gestão de pessoas para Procuradoria Geral do Estado, com funcionalidades de carreira, capacitação, desempenho, diversidade, benefícios e movimentação.

## Entidades Implementadas

### 1. Gestão de Carreira
- **CareerStage**: Estágios da carreira (Probatório, Classes, etc.)
- **CareerEvaluation**: Avaliações de carreira com scoring de mérito
- **EmployeeCareer**: Histórico funcional do servidor

### 2. Cadastro de Servidores
- **Employee**: Dados cadastrais completos (incluindo diversidade)

### 3. Avaliação de Desempenho
- **PerformanceEvaluation**: Indicadores individuais de produtividade

### 4. Capacitação (Integração ESAP)
- **TrainingEvent**: Eventos de capacitação
- **TrainingEnrollment**: Inscrições e certificados

### 5. Benefícios e Welfare
- **EmployeeBenefit**: Benefícios (gratificação científica, auxílios, diárias)

### 6. Frequência e Jornada
- **AttendanceRecord**: Controle de ponto e teletrabalho

### 7. Movimentação e Lotação
- **MovementRequest**: Remoções, redistribuições, lotações

### 8. Diversidade e Inclusão (Integração CEDI)
- **DiversityIndicator**: Métricas de gênero, raça, PCD, LGBTQIA+, faixa etária

## Critérios de Merecimento (Scoring)

| Critério | Pontuação Máxima | Descrição |
|----------|------------------|-----------|
| Competência Profissional | 20 pts | 10-20 atos dos últimos 12 meses |
| Eficiência no Exercício | 30 pts | Quantidade de atos, sustentações orais, prazos |
| Dedicação e Pontualidade | 25 pts | Assiduidade, inovações, publicações |
| Aprimoramento Cultural | 10 pts | Pós-graduação, artigos, conferências |
| Conduta Ética | 15 pts | Ausência de PAD, representações |
| **TOTAL** | **100 pts** | |

## Indicadores de Desempenho Individuais

| Indicador | Fórmula | Meta Anual | Fonte |
|-----------|---------|------------|-------|
| Produtividade | Atos judiciais + administrativos | 1.227 atos/procurador | Corregedoria |
| Processos Concluídos | Processos arquivados/transitados | 19.875 processos/ano | TJMA/SIMMA |
| Prazos Cumpridos | (Prazos em dia / Total) × 100 | 95% | SPA/PJE |
| Qualidade Técnica | Recursos × Taxa de provimento | < 15% improcedentes | TJMA |
| Capacitação | Horas de treinamento/ano | 40 horas | ESAP |

## Base Legal Integrada

- **LC 20/1994**: Lei Orgânica da PGE-MA
- **Resolução 02/2017-CSPGE**: Remoção voluntária
- **Portaria GAB/PGE nº 17/2024**: INOVA-PGE-MA
- **Resolução CSPGE-MA nº 01/2024**: CEDI/PGE (Diversidade)
- **Decreto 39.054/2024**: Combate ao assédio
- **Lei 13.146/2015**: Estatuto da Pessoa com Deficiência

## Próximos Passos

1. Criar repositórios restantes (CareerStage, CareerEvaluation, TrainingEvent, etc.)
2. Implementar serviços com regras de negócio
3. Criar controllers REST
4. Desenvolver DTOs para API
5. Implementar Liquibase migrations
6. Criar testes unitários

## Integrações Necessárias

| Sistema | Finalidade | Tipo |
|---------|------------|------|
| SEI | Processos administrativos de RH | API/Webservice |
| SIMMA/SEPLAN | Monitoramento de metas físicas | Exportação mensal |
| SIGEF/SIGA | Folha de pagamento | API Financeira |
| SPA/PJE | Produtividade de procuradores | API Jurídica |
| ESAP | Capacitações e certificados | Banco de Dados |
| CEDI/PGE | Indicadores de diversidade | Dashboard |
| Corregedoria | Avaliações e correições | API |
