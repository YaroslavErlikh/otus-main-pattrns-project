CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255),
    login    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);
