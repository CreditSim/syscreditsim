-- Credenciais de conexão do PostgreSQL (comentadas para referência)
-- HOST: localhost
-- PORT: 5432
-- DATABASE: sample_db
-- USER: your_username
-- PASSWORD: your_password

-- Desativar restrições de chaves estrangeiras temporariamente
-- No PostgreSQL, não há uma opção direta para desativar todas as restrições de chaves estrangeiras
-- Portanto, é necessário garantir que a ordem de execução dos comandos SQL seja correta
-- e que as tabelas sejam criadas na ordem correta para evitar erros de referência.

-- ----------------------------
-- Estrutura da tabela positions
-- ----------------------------
DROP TABLE IF EXISTS positions;
CREATE TABLE positions (
  positions_id SERIAL PRIMARY KEY,
  positions_name VARCHAR(255)
);

-- ----------------------------
-- Inserção de dados na tabela positions
-- ----------------------------
INSERT INTO positions (positions_id, positions_name) VALUES
(1, 'Lucas Silva'),
(2, 'Gabriel Oliveira'),
(3, 'Mateus Santos'),
(4, 'Rafael Ferreira'),
(5, 'André Costa'),
(6, 'Felipe Almeida'),
(7, 'Bruno Pereira'),
(8, 'Ricardo Mendes'),
(9, 'Thiago Rocha'),
(10, 'Eduardo Martins');
-- ----------------------------
-- Estrutura da tabela employee
-- ----------------------------
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  employee_id SERIAL PRIMARY KEY,
  employee_name VARCHAR(255),
  location VARCHAR(255),
  date DATE,
  salary NUMERIC(10, 2),
  description VARCHAR(255),
  positions_id INT NOT NULL,
  CONSTRAINT fk_positions FOREIGN KEY (positions_id)
  REFERENCES positions(positions_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Inserção de dados na tabela employee
-- ----------------------------
INSERT INTO employee (employee_name, location, date, salary, description, positions_id)
VALUES ('Thiago Gomes', 'Rua Ceará, 126, Jardim America', '2024-10-28', 10, 'Mesa Home Office', 3);

-- Não é necessário reativar as restrições de chaves estrangeiras porque não as desativamos explicitamente
