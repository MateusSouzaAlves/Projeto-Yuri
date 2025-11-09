# CRUD JDBC MVC (Jakarta EE) — Documentação

---

## 1) Visão Geral

Aplicação web simples para **cadastro de produtos** e **avaliações** (reviews), com **login** básico. A persistência usa **MySQL** via JDBC.

### Principais funcionalidades

* Login/Logout.
* CRUD de Produtos (listar, criar, editar, excluir).
* Avaliações por produto (listar e inserir).

---

## 2) Arquitetura e Organização

```
crudjdbcyoutube 4/
├─ src/java/
│  ├─ controller/            # Servlets (Controllers MVC)
│  │  ├─ LoginController.java
│  │  ├─ ProdutoController.java
│  │  └─ AvaliacaoController.java
│  ├─ dao/                   # DAOs (acesso a dados via JDBC)
│  │  ├─ UsuarioDAO.java
│  │  ├─ ProdutoDAO.java
│  │  └─ AvaliacaoDAO.java
│  ├─ model/                 # POJOs (Entidades)
│  │  ├─ Usuario.java
│  │  ├─ Produto.java
│  │  └─ Avaliacao.java
│  └─ utils/
│     └─ ConnectionFactory.java
├─ web/                      # Web root (JSPs, CSS, WEB-INF)
│  ├─ index.jsp
│  ├─ includes/
│  │  ├─ header.jsp
│  │  └─ footer.jsp
│  ├─ views/
│  │  ├─ login/login.jsp
│  │  ├─ produtos/
│  │  │  ├─ produto-listar.jsp
│  │  │  ├─ produto-cadastro.jsp
│  │  │  └─ produto-avaliar.jsp
│  │  └─ avaliacoes/avaliacao-listar.jsp
│  └─ WEB-INF/
│     └─ web.xml             # Descriptor (Jakarta EE 10 — web-app_6_0)
└─ lib/ (opcional)           # JARs locais quando não se usa Maven
```

* **Controller**: classes Servlet que recebem as requisições e chamam os DAOs/Views.
* **DAO**: executam SQL via `ConnectionFactory`.
* **Model**: POJOs simples com getters/setters.
* **Views**: JSP + JSTL (laços/condições/renderização).

---

## 3) Requisitos de Ambiente

* **JDK**: 17 (recomendado para Tomcat 10.1.x)
* **Servidor**: **Apache Tomcat 10.1+**
* **Banco**: **MySQL 8+**
* **Driver JDBC**: `mysql-connector-j 8.x` (copiar para `WEB-INF/lib`)

---

## 4) Configuração do Banco (MySQL)

A aplicação espera um schema `exemplomvcyoutube` e conexão padrão **`root`/`root`** (veja `utils/ConnectionFactory.java`). **Ajuste usuário/senha/host conforme seu ambiente.**

### 4.1 SQL — criação e seed

> **Cole diretamente no MySQL** (Workbench/CLI).

```sql
CREATE DATABASE IF NOT EXISTS exemplomvcyoutube
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE exemplomvcyoutube;

CREATE TABLE IF NOT EXISTS Produto (
  idProduto     INT AUTO_INCREMENT PRIMARY KEY,
  nomeProduto   VARCHAR(100) NOT NULL,
  qtdProduto    INT NOT NULL,
  valorProduto  DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

INSERT INTO Produto (nomeProduto, qtdProduto, valorProduto) VALUES
('Teclado Mecânico', 10, 299.90),
('Mouse Gamer', 25, 159.50),
('Headset', 15, 249.00);

CREATE TABLE usuario (
  idUsuario INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(100) NOT NULL
);

INSERT INTO usuario (nome, email, senha) VALUES
('Admin', 'admin@teste.com', '1234');

DROP TABLE IF EXISTS avaliacao;
CREATE TABLE avaliacao (
  idAvaliacao INT AUTO_INCREMENT PRIMARY KEY,
  idProduto   INT NOT NULL,
  nomeUsuario VARCHAR(100) NOT NULL,
  comentario  TEXT,
  nota        INT NOT NULL,
  createdAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_avaliacao_produto
    FOREIGN KEY (idProduto) REFERENCES Produto(idProduto)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT chk_nota CHECK (nota BETWEEN 1 AND 5),
  INDEX idx_idProduto (idProduto)
);

INSERT INTO avaliacao (idProduto, nomeUsuario, comentario, nota)
VALUES
(1, 'Mateus', 'Excelente teclado', 5),
(1, 'Priscila', 'Gostei, mas podia ser ABNT2', 4),
(2, 'João', 'Mouse bom pelo preço', 4);

-- Consulta de avaliações com nome do produto
SELECT
  a.idAvaliacao,
  a.idProduto,
  a.nomeUsuario,
  a.comentario,
  a.nota,
  p.nomeProduto AS nomeProduto
FROM avaliacao a
LEFT JOIN Produto p ON p.idProduto = a.idProduto
ORDER BY a.idAvaliacao DESC;
```

---

## 5) Configuração da Aplicação

### 5.1 `ConnectionFactory.java`

Arquivo em `src/java/utils/ConnectionFactory.java`. Ajuste `url`, `user`, `password` conforme sua máquina:

```java
String url = "jdbc:mysql://localhost/exemplomvcyoutube?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
Connection conn = DriverManager.getConnection(url, "root", "root");
```

> **Dica**: extraia credenciais para um `.properties` ou variáveis de ambiente.

### 5.2 `web.xml` (Jakarta EE 10)

O projeto usa `web-app_6_0`. **Garanta Tomcat 10.1+**. Mapeamentos relevantes:

* **LoginController** → `/login`, `/logout`
* **ProdutoController** → `/produtos`, `/produtos/novo`, `/produtos/salvar`, `/produtos/editar`, `/produtos/excluir`, `/produtos/avaliar`
* **AvaliacaoController** → `/avaliacoes`, `/avaliacoes/listar`, `/avaliacoes/salvar`

> A `welcome-file` aponta para `views/login/login.jsp`.

### 5.3 Dependências (sem Maven)

Coloque em **`web/WEB-INF/lib/`**:

* `mysql-connector-j-8.x.jar`
* `jakarta.servlet.jsp.jstl-3.0.x.jar` e `jakarta.servlet.jsp.jstl-api-3.0.x.jar`

---

## 6) Fluxos Principais (Controllers)

### 6.1 LoginController

* **GET /login** → exibe `views/login/login.jsp`.
* **POST /login** → valida com `UsuarioDAO.autenticar(email, senha)`; cria `session.setAttribute("usuarioLogado", user)`; redireciona para `/produtos`.
* **GET /logout** → invalida sessão; redireciona para `/login`.

### 6.2 ProdutoController

* **GET /produtos** → lista via `ProdutoDAO.listar()` e encaminha para `produto-listar.jsp`.
* **GET /produtos/novo** → mostra `produto-cadastro.jsp`.
* **POST /produtos/salvar** → cria/edita produto com `ProdutoDAO.inserir/atualizar`.
* **GET /produtos/editar?id=...** → carrega produto e exibe form.
* **GET /produtos/excluir?id=...** → `ProdutoDAO.excluir(id)`.
* **GET /produtos/avaliar?idProduto=...** → abre `produto-avaliar.jsp`.

### 6.3 AvaliacaoController

* **GET /avaliacoes/listar?idProduto=...** → usa `AvaliacaoDAO.listarPorProduto(idProduto)`; exibe `avaliacao-listar.jsp`.
* **POST /avaliacoes/salvar** → `AvaliacaoDAO.inserir(...)`; redireciona de volta para as avaliações do produto.

---

## 7) DAOs (SQL resumido)

* **UsuarioDAO**: `SELECT * FROM usuario WHERE email=? AND senha=?`.
* **ProdutoDAO**:

  * Insert: `INSERT INTO Produto (nomeProduto, qtdProduto, valorProduto) VALUES (?,?,?)`
  * Update: `UPDATE Produto SET nomeProduto=?, qtdProduto=?, valorProduto=? WHERE idProduto=?`
  * Delete: `DELETE FROM Produto WHERE idProduto=?`
  * List/Find: `SELECT * FROM Produto` / `SELECT * FROM Produto WHERE idProduto=?`
* **AvaliacaoDAO**:

  * Insert: `INSERT INTO avaliacao (idProduto, nomeUsuario, comentario, nota) VALUES (?,?,?,?)`
  * List por produto (com nome do produto):

    ```sql
    SELECT a.*, p.nomeProduto
    FROM avaliacao a
    JOIN Produto p ON p.idProduto = a.idProduto
    WHERE a.idProduto = ?
    ORDER BY a.idAvaliacao DESC
    ```

---

## 8) JSPs — Observações Importantes

* Inclua `header.jsp`/`footer.jsp` via `<jsp:include>`.
* Troque **URIs JSTL** para Jakarta (`jakarta.tags.core`).
* Exemplo de topo de uma JSP que usa JSTL **correto**:

```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

* Use `${pageContext.request.contextPath}` para montar links.

---

## 9) Segurança e Boas Práticas

* **Nunca** salve senhas em texto puro (use hash `BCrypt`/`Argon2`).
* Valide entrada do usuário (tamanho, faixa da `nota`, etc.).
* Trate `SQLException` e feche recursos (use *try-with-resources* já adotado no código).
* Parametrize conexão via `.properties` e **não** versionar credenciais.

---

## 10) Roadmap (sugestões)

* Migrar para **Maven/Gradle** e gerenciar dependências corretamente.
* Autenticação segura com hash de senha e filtros de autorização.
* Paginação e validações no lado servidor/cliente.
* DTOs/Services para separar melhor regras de negócio dos Servlets.
* Testes unitários para DAOs (Testcontainers + MySQL).
