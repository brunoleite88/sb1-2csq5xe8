# Módulos Complementares - PGE Strategic Platform

## Visão Geral

O sistema PGE Strategic Platform inclui dois módulos complementares que podem ser habilitados/desabilitados através de permissões específicas:

1. **Módulo de Inovação** - Acompanhamento de Projetos de Inovação
2. **Módulo de Equidade, Diversidade e Inclusão** - Gestão de indicadores DE&I

---

## 1. Módulo de Inovação

### Funcionalidades

#### 1.1 Tracking de Projetos de Inovação (7 Etapas)
- **Identificação**: Levantamento de problemas e oportunidades
- **Ideação**: Geração de ideias criativas
- **Seleção**: Avaliação e priorização
- **Prototipagem**: Desenvolvimento de provas de conceito
- **Sandbox Regulatório**: Testes em ambiente controlado
- **Ampliação**: Expansão para áreas piloto
- **Efetivação**: Implementação definitiva

#### 1.2 Gestão do Prêmio de Inovação
- Controle de inscrições por edição
- Avaliação de projetos indicados
- Registro de vencedores e justificativas
- Gestão de cerimônias de premiação

#### 1.3 Inventário de Tecnologias Implementadas
- Registro de IA, RPA, Machine Learning
- Documentação de benefícios e economia
- Link com projetos de origem
- Métricas de impacto (usuários, economia)

### Entidades
- `ProjetoInovacao` - Projetos nas 7 etapas
- `PremioInovacao` - Edições do prêmio
- `TecnologiaImplementada` - Inventário tecnológico

### APIs REST
```
GET    /api/inovacao/projetos              - Listar todos
GET    /api/inovacao/projetos/{id}         - Buscar por ID
GET    /api/inovacao/projetos/etapa/{etapa}- Filtrar por etapa
GET    /api/inovacao/projetos/meus-projetos- Projetos do usuário
GET    /api/inovacao/projetos/estatisticas - Dashboard por etapa
POST   /api/inovacao/projetos              - Criar projeto
PUT    /api/inovacao/projetos/{id}         - Atualizar projeto
PATCH  /api/inovacao/projetos/{id}/avancar-etapa - Avançar etapa
DELETE /api/inovacao/projetos/{id}         - Excluir (lógico)
GET    /api/inovacao/projetos/premio/indicados - Indicados ao prêmio
```

### Permissões
- `INOVACAO_VISUALIZAR`
- `INOVACAO_CRIAR`
- `INOVACAO_EDITAR`
- `INOVACAO_EXCLUIR`
- `INOVACAO_GERENCIAR_PREMIO`
- `INOVACAO_GERENCIAR_TECNOLOGIAS`

---

## 2. Módulo de Equidade, Diversidade e Inclusão

### Funcionalidades

#### 2.1 Indicadores de Diversidade
- **Gênero**: Feminino, Masculino, Não-binário
- **Raça/Etnia**: Branca, Preta, Parda, Amarela, Indígena
- **Orientação Sexual**: Hetero, Homo, Bi, Outros
- **Geracional**: Faixas etárias (até 25, 26-35, 36-45, 46-55, 55+)
- **PCD**: Pessoas com Deficiência

#### 2.2 Gestão de Grupos de Trabalho Setoriais
- Criação de grupos por temática
- Designação de coordenadores e membros
- Vinculação a unidades organizacionais
- Definição de objetivos e escopo

#### 2.3 Agenda de Eventos
- Registro de palestras, workshops, cursos
- Controle de participação
- Materiais e resultados disponíveis
- Integração com grupos de trabalho

#### 2.4 Relatórios de Conformidade Legal
- Fundamentação legal (leis, decretos, portarias)
- Análise de conformidade
- Recomendações e planos corretivos
- Fluxo de aprovação e publicação

### Entidades
- `IndicadorDiversidade` - Métricas DE&I
- `GrupoTrabalhoEquidade` - Grupos setoriais
- `EventoEquidade` - Agenda de eventos
- `RelatorioConformidadeLegal` - Conformidade legal

### APIs REST
```
GET    /api/equidade/indicadores                    - Listar todos
GET    /api/equidade/indicadores/{id}               - Buscar por ID
GET    /api/equidade/indicadores/unidade/{id}       - Por unidade
GET    /api/equidade/indicadores/tipo/{tipo}        - Por tipo
GET    /api/equidade/indicadores/periodo            - Por período
GET    /api/equidade/indicadores/relatorio-consolidado - Relatório
POST   /api/equidade/indicadores                    - Criar indicador
PUT    /api/equidade/indicadores/{id}               - Atualizar
DELETE /api/equidade/indicadores/{id}               - Excluir (lógico)
```

### Permissões
- `EQUIDADE_VISUALIZAR`
- `EQUIDADE_CRIAR`
- `EQUIDADE_EDITAR`
- `EQUIDADE_GERENCIAR_GRUPOS`
- `EQUIDADE_GERENCIAR_EVENTOS`
- `EQUIDADE_GERENCIAR_RELATORIOS`

---

## Instalação e Configuração

### Banco de Dados
O script `004-modulos-complementares.sql` cria automaticamente:
- 7 tabelas novas
- Relacionamentos com entidades existentes
- 12 novas permissões no sistema

### Habilitação de Módulos
Os módulos são habilitados automaticamente quando:
1. As permissões são atribuídas a roles/usuários
2. O Liquibase executa as migrations
3. Os controllers são scanneados pelo Spring

### Customização pelo Administrador
O administrador pode:
- Habilitar/desabilitar módulos por perfil de acesso
- Customizar labels e descrições das etapas
- Configurar fluxos de aprovação
- Definir políticas de visibilidade de dados

---

## Próximos Passos Sugeridos

1. Implementar controllers restantes (PremioInovacao, TecnologiaImplementada, GrupoTrabalho, Evento, RelatorioConformidade)
2. Criar DTOs específicos para relatórios consolidados
3. Desenvolver frontend React para os novos módulos
4. Implementar dashboards analíticos
5. Criar testes unitários e de integração
6. Documentar endpoints na Swagger/OpenAPI
