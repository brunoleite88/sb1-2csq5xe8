-- Módulo de Inovação
CREATE TABLE IF NOT EXISTS projeto_inovacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    etapa_atual VARCHAR(50) NOT NULL,
    responsavel_id BIGINT,
    unidade_organizacional_id BIGINT,
    tecnologias_utilizadas VARCHAR(500),
    orcamento_estimado DECIMAL(10,2),
    data_inicio DATETIME,
    data_previsao_conclusao DATETIME,
    data_efetivacao DATETIME,
    resultados_alcancados TEXT,
    indica_premio_inovacao BOOLEAN DEFAULT FALSE,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (responsavel_id) REFERENCES app_user(id),
    FOREIGN KEY (unidade_organizacional_id) REFERENCES organizational_unit(id)
);

CREATE TABLE IF NOT EXISTS premio_inovacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_edicao VARCHAR(200) NOT NULL,
    regulamento TEXT,
    data_inicio_inscricoes DATETIME,
    data_fim_inscricoes DATETIME,
    status VARCHAR(50) NOT NULL,
    numero_inscricoes INT DEFAULT 0,
    projeto_vencedor_id BIGINT,
    justificativa_premiacao TEXT,
    valor_premio DECIMAL(10,2),
    data_cerimonia DATETIME,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    FOREIGN KEY (projeto_vencedor_id) REFERENCES projeto_inovacao(id)
);

CREATE TABLE IF NOT EXISTS tecnologia_implementada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_tecnologia VARCHAR(200) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descricao TEXT,
    fornecedor VARCHAR(500),
    data_implementacao DATETIME,
    unidade_responsavel_id BIGINT,
    projeto_origem_id BIGINT,
    numero_usuarios_impactados INT,
    economia_estimada DECIMAL(10,2),
    beneficios_alcancados TEXT,
    documentacao_disponivel BOOLEAN DEFAULT TRUE,
    link_documentacao VARCHAR(500),
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (unidade_responsavel_id) REFERENCES organizational_unit(id),
    FOREIGN KEY (projeto_origem_id) REFERENCES projeto_inovacao(id)
);

-- Módulo de Equidade, Diversidade e Inclusão
CREATE TABLE IF NOT EXISTS indicador_diversidade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    unidade_organizacional_id BIGINT,
    total_servidores INT,
    quantidade_genero_feminino INT,
    quantidade_genero_masculino INT,
    quantidade_genero_nao_binario INT,
    quantidade_raca_branca INT,
    quantidade_raca_preta INT,
    quantidade_raca_parda INT,
    quantidade_raca_amarela INT,
    quantidade_raca_indigena INT,
    quantidade_orientacao_heterossexual INT,
    quantidade_orientacao_homossexual INT,
    quantidade_orientacao_bisexual INT,
    quantidade_orientacao_outros INT,
    quantidade_geracao_ate25anos INT,
    quantidade_geracao_26a35anos INT,
    quantidade_geracao_36a45anos INT,
    quantidade_geracao_46a55anos INT,
    quantidade_geracao_acima55anos INT,
    quantidade_pcd INT,
    observacoes TEXT,
    data_referencia DATETIME,
    responsavel_preenchimento_id BIGINT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (unidade_organizacional_id) REFERENCES organizational_unit(id),
    FOREIGN KEY (responsavel_preenchimento_id) REFERENCES app_user(id)
);

CREATE TABLE IF NOT EXISTS grupo_trabalho_equidade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    objetivo TEXT,
    escopo_atuacao TEXT,
    tipo VARCHAR(50) NOT NULL,
    coordenador_id BIGINT,
    unidade_vinculada_id BIGINT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (coordenador_id) REFERENCES app_user(id),
    FOREIGN KEY (unidade_vinculada_id) REFERENCES organizational_unit(id)
);

CREATE TABLE IF NOT EXISTS grupo_trabalho_membros (
    grupo_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (grupo_id, usuario_id),
    FOREIGN KEY (grupo_id) REFERENCES grupo_trabalho_equidade(id),
    FOREIGN KEY (usuario_id) REFERENCES app_user(id)
);

CREATE TABLE IF NOT EXISTS evento_equidade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50) NOT NULL,
    data_inicio DATETIME,
    data_fim DATETIME,
    local VARCHAR(500),
    online BOOLEAN DEFAULT FALSE,
    link_acesso VARCHAR(500),
    organizador_id BIGINT,
    grupo_trabalho_id BIGINT,
    numero_participantes_estimado INT,
    numero_participantes_real INT,
    resultados_alcancados TEXT,
    material_disponivel TEXT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (organizador_id) REFERENCES app_user(id),
    FOREIGN KEY (grupo_trabalho_id) REFERENCES grupo_trabalho_equidade(id)
);

CREATE TABLE IF NOT EXISTS relatorio_conformidade_legal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(300) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50) NOT NULL,
    periodo_inicio DATETIME,
    periodo_fim DATETIME,
    fundamentacao_legal TEXT,
    analise_conformidade TEXT,
    recomendacoes TEXT,
    plano_acao_corretivo TEXT,
    elaborador_id BIGINT,
    aprovador_id BIGINT,
    status VARCHAR(50) NOT NULL,
    data_submissao DATETIME,
    data_aprovacao DATETIME,
    caminho_arquivo VARCHAR(500),
    publicado BOOLEAN DEFAULT FALSE,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME,
    FOREIGN KEY (elaborador_id) REFERENCES app_user(id),
    FOREIGN KEY (aprovador_id) REFERENCES app_user(id)
);

-- Inserção de permissões para os novos módulos
INSERT INTO permission (name, description, module_name) VALUES
('INOVACAO_VISUALIZAR', 'Visualizar projetos de inovação', 'INOVACAO'),
('INOVACAO_CRIAR', 'Criar projetos de inovação', 'INOVACAO'),
('INOVACAO_EDITAR', 'Editar projetos de inovação', 'INOVACAO'),
('INOVACAO_EXCLUIR', 'Excluir projetos de inovação', 'INOVACAO'),
('INOVACAO_GERENCIAR_PREMIO', 'Gerenciar prêmio de inovação', 'INOVACAO'),
('INOVACAO_GERENCIAR_TECNOLOGIAS', 'Gerenciar inventário de tecnologias', 'INOVACAO'),
('EQUIDADE_VISUALIZAR', 'Visualizar indicadores de diversidade', 'EQUIDADE'),
('EQUIDADE_CRIAR', 'Criar indicadores de diversidade', 'EQUIDADE'),
('EQUIDADE_EDITAR', 'Editar indicadores de diversidade', 'EQUIDADE'),
('EQUIDADE_GERENCIAR_GRUPOS', 'Gerenciar grupos de trabalho', 'EQUIDADE'),
('EQUIDADE_GERENCIAR_EVENTOS', 'Gerenciar eventos de equidade', 'EQUIDADE'),
('EQUIDADE_GERENCIAR_RELATORIOS', 'Gerenciar relatórios de conformidade', 'EQUIDADE');
