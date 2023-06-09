CREATE SCHEMA IF NOT EXISTS dev;

CREATE TABLE tb_person
(
    id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(50) NOT NULL,
    date_of_birth   DATE        NOT NULL,
    cpf             VARCHAR(11) NOT NULL,
    email           VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;