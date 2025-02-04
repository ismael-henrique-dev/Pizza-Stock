create database javaConnectionTeste;

use javaConnectionTeste;

create table tbAdmin (
	id int primary key auto_increment,
    nome varchar(50),
    senha varchar(50),
    email varchar(100)
);

CREATE TABLE tbSessions (
    idSession INT PRIMARY KEY AUTO_INCREMENT,
    idAdmin INT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tbItem (
    codItem INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(55) NOT NULL,
    quantidade_ocup INT NOT NULL,
    preco DOUBLE NOT NULL,
    peso DOUBLE NOT NULL,
    quantidade_max INT NOT NULL,
    createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TbRelatorios (
	id int primary key auto_increment,
    total_gasto double,
    lucro_total double,
    quantidade_pizzas int,
    espaco_estoque_atualmente double,
	createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

#alterei aqui
ALTER TABLE tbItem ADD COLUMN idAdmin INT;
ALTER TABLE tbItem ADD constraint FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);

ALTER TABLE TbRelatorios ADD COLUMN idAdmin int;
ALTER TABLE TbRelatorios ADD constraint FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);

ALTER TABLE tbSessions ADD FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);
