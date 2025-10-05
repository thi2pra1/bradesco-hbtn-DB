-- SQL Schema for database_admin.db
-- Generated from JPA Entity mappings

-- Table: pessoa
CREATE TABLE pessoa (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    idade INTEGER,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE
);

-- Table: produto
CREATE TABLE produto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    quantidade INTEGER,
    preco REAL,
    status BOOLEAN
);

-- Indexes for better performance
CREATE INDEX idx_pessoa_cpf ON pessoa(cpf);
CREATE INDEX idx_pessoa_email ON pessoa(email);
CREATE INDEX idx_produto_nome ON produto(nome);
CREATE INDEX idx_produto_status ON produto(status);
