-- Script de inicialização do banco de dados PGE Strategic
-- Executado automaticamente na primeira inicialização do container

CREATE DATABASE IF NOT EXISTS pge_strategic CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pge_strategic;

-- Usuário padrão para acesso da aplicação
CREATE USER IF NOT EXISTS 'pge_user'@'%' IDENTIFIED BY 'pge_secure_2025_change_in_prod';
GRANT ALL PRIVILEGES ON pge_strategic.* TO 'pge_user'@'%';
FLUSH PRIVILEGES;

-- Tabela de controle de versão do schema
CREATE TABLE IF NOT EXISTS schema_version (
    version_rank INT,
    installed_rank INT,
    version VARCHAR(50),
    description VARCHAR(200),
    type VARCHAR(20),
    script VARCHAR(1000),
    checksum INT,
    installed_by VARCHAR(100),
    installed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    execution_time INT,
    success BOOLEAN,
    PRIMARY KEY (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Mensagem de confirmação
SELECT 'Banco de dados pge_strategic inicializado com sucesso!' AS status;
