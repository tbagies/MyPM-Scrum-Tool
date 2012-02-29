DROP DATABASE IF EXISTS banktransaction;

CREATE DATABASE banktransaction;

USE banktransaction;

CREATE TABLE UserAccount (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  balance DOUBLE,
  PRIMARY KEY(id)
);
  
INSERT INTO UserAccount VALUES (100, 'Taghreed', 150.0);
