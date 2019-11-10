CREATE TABLE category (
    code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (title) values ('Recreation');
INSERT INTO category (title) values ('Feeding');
INSERT INTO category (title) values ('Supermarket');
INSERT INTO category (title) values ('Drugstore');
INSERT INTO category (title) values ('Others');