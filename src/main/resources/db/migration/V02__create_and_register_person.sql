CREATE TABLE person (
    code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    active BIT NOT NULL,
    publicarea VARCHAR(50),
    number VARCHAR(10),
    type VARCHAR(30),
    neighborhood VARCHAR(50),
    zip VARCHAR(10),
    city VARCHAR(50),
    state VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO person (name, active, publicarea, number, type, neighborhood, zip, city, state)
        values ('João Vitor Soares', 1, "Rua Bernardino de Campos", "873", "Casa", "Campo Belo", "04620003", "São Paulo", "São Paulo");

INSERT INTO person (name, active, publicarea, number, type, neighborhood, zip, city, state)
        values ('Luana Freitas', 1, "Rua Bernardino de Campos", "873", "Casa", "Campo Belo", "04620003", "São Paulo", "São Paulo");

INSERT INTO person (name, active, publicarea, number, type, neighborhood, zip, city, state)
        values ('Fulano de Tal', 1, "Rua Moacir", "2468", "Casa", "Campo Belo", "06545010", "São Paulo", "São Paulo");

INSERT INTO person (name, active, publicarea, number, type, neighborhood, zip, city, state)
        values ('Marcelo Carlos', 1, "Rua das Oliveiras", "36", "Casa", "Campo Belo", "05890005", "São Paulo", "São Paulo");
