CREATE TABLE entry (
    code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    experydate DATE NOT NULL,
    paymentdate DATE,
    entryvalue DECIMAL(10,2) NOT NULL,
    observation VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    categorycode BIGINT(20) NOT NULL,
    personcode BIGINT(20) NOT NULL,
    FOREIGN KEY (categorycode) REFERENCES category(code),
    FOREIGN KEY (personcode) REFERENCES person(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO entry (description, experydate, paymentdate, entryvalue, observation, type, categorycode, personcode)
    values ("Mensal salary", "2017-06-10", null, 6500.00, "Profit distribution", "INCOME", 1, 1);

INSERT INTO entry (description, experydate, paymentdate, entryvalue, observation, type, categorycode, personcode)
    values ("Bahamas", "2017-02-10", "2017-02-10", 100.32, null, "EXPENSE", 2, 2);

INSERT INTO entry (description, experydate, paymentdate, entryvalue, observation, type, categorycode, personcode)
    values ("Top Club", "2017-06-10", null, 120, null, "INCOME", 3, 3);