# Sistem — Controle de Usuários e Finanças (Java + MySQL)

Sistema desktop desenvolvido em **Java (Swing)** com **MySQL**, focado em práticas de **Back-end** e **Banco de Dados**: autenticação, controle de permissões (Admin/Usuário), CRUD e relacionamento entre tabelas.

## 🚀 Funcionalidades

### ✅ Usuários
- Cadastro de usuário (nome, e-mail, senha, admin)
- Login com validação
- Controle de permissão:
  - **Admin:** gerencia usuários (listar, adicionar, editar, excluir)
  - **Usuário:** visualiza apenas seus próprios dados/finanças

### ✅ Finanças
- Registro de valores vinculados a um usuário (`usuario_id`)
- Listagem de finanças **somente do usuário logado**
- (Opcional) Total por usuário / relatórios

## 🧱 Tecnologias
- Java (JDK)
- Swing (Interface)
- JDBC (Conexão com banco)
- MySQL
- Maven

## 🗃️ Modelo do Banco de Dados

### SQL de criação
```sql
CREATE DATABASE sistem;
USE sistem;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    adm TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE financas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL,
    usuario_id INT NOT NULL,
    CONSTRAINT fk_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE
);

 Melhorias planejadas (Roadmap)

 Senhas com hash (BCrypt)

 Não exibir senha na tela/admin

 Validações (e-mail, senha forte, campos vazios)

 Melhorar arquitetura (DAO/Service)

 Relatórios: saldo total por usuário (SUM)

 Exportar relatório (PDF/Excel)
