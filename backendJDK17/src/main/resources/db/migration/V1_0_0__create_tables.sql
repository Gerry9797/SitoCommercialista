CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Creazione tabella users
CREATE TABLE users
(
    id         VARCHAR(36) NOT NULL,
    email      VARCHAR(255) UNIQUE,
    password   VARCHAR(255),
    username   VARCHAR(255) UNIQUE,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

-- Creazione tabella accounts
CREATE TABLE accounts
(
    id_user           VARCHAR(36) NOT NULL,
    data_pubb         TIMESTAMP   NOT NULL,
    status            VARCHAR(50),
    verification_code VARCHAR(255),
    CONSTRAINT accounts_pkey PRIMARY KEY (id_user),
    CONSTRAINT accounts_status_check CHECK (status IN ('ATTIVO', 'NON_ATTIVO', 'SOSPESO')),
    CONSTRAINT accounts_user_fk FOREIGN KEY (id_user) REFERENCES users (id)
);

-- Creazione tabella roles
CREATE TABLE roles
(
    id     VARCHAR(50) NOT NULL,
    denominazione   VARCHAR(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

-- Creazione tabella user_roles
CREATE TABLE user_roles
(
    user_id VARCHAR(36) NOT NULL,
    role_id VARCHAR(50) NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_roles_role_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Creazione dei RUOLI nella tabella roles
INSERT INTO roles (id, denominazione)
VALUES
    ('ROLE_USER', 'User'),
    ('ROLE_MODERATOR', 'Moderator'),
    ('ROLE_ADMIN', 'Admin');

