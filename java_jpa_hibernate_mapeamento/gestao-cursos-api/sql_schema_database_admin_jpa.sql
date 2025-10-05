-- SQL Schema for database_admin_jpa.db
-- Generated from JPA Entity mappings for Course Management System

-- Table: aluno
CREATE TABLE aluno (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE,
    matricula VARCHAR(20) UNIQUE
);

-- Table: professor
CREATE TABLE professor (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE,
    especializacao VARCHAR(200),
    registro_professor VARCHAR(20) UNIQUE
);

-- Table: material_curso
CREATE TABLE material_curso (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(500),
    tipo_material VARCHAR(50),
    url_recurso VARCHAR(300),
    autor VARCHAR(100)
);

-- Table: curso
CREATE TABLE curso (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    carga_horaria INTEGER,
    data_inicio DATE,
    data_fim DATE,
    codigo_curso VARCHAR(20) UNIQUE,
    professor_id INTEGER NOT NULL,
    material_id INTEGER,
    FOREIGN KEY (professor_id) REFERENCES professor(id),
    FOREIGN KEY (material_id) REFERENCES material_curso(id)
);

-- Table: endereco
CREATE TABLE endereco (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    rua VARCHAR(200) NOT NULL,
    numero VARCHAR(10),
    bairro VARCHAR(100),
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2),
    cep VARCHAR(10),
    aluno_id INTEGER,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

-- Table: telefone
CREATE TABLE telefone (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ddd VARCHAR(3),
    numero VARCHAR(15) NOT NULL,
    tipo VARCHAR(20),
    aluno_id INTEGER,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

-- Table: curso_aluno (Many-to-Many relationship table)
CREATE TABLE curso_aluno (
    curso_id INTEGER NOT NULL,
    aluno_id INTEGER NOT NULL,
    PRIMARY KEY (curso_id, aluno_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id),
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

-- Indexes for better performance
CREATE INDEX idx_aluno_cpf ON aluno(cpf);
CREATE INDEX idx_aluno_matricula ON aluno(matricula);
CREATE INDEX idx_aluno_email ON aluno(email);

CREATE INDEX idx_professor_cpf ON professor(cpf);
CREATE INDEX idx_professor_registro ON professor(registro_professor);
CREATE INDEX idx_professor_email ON professor(email);

CREATE INDEX idx_curso_codigo ON curso(codigo_curso);
CREATE INDEX idx_curso_professor ON curso(professor_id);
CREATE INDEX idx_curso_material ON curso(material_id);

CREATE INDEX idx_endereco_aluno ON endereco(aluno_id);
CREATE INDEX idx_telefone_aluno ON telefone(aluno_id);

CREATE INDEX idx_curso_aluno_curso ON curso_aluno(curso_id);
CREATE INDEX idx_curso_aluno_aluno ON curso_aluno(aluno_id);
