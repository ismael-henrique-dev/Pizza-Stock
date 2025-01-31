create database javaConnectionTeste;

use javaConnectionTeste;

create table tbAdmin (
	id int primary key auto_increment,
    nome varchar(50),
    login varchar(50),
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

create table tbEstoque (
	id int primary key auto_increment,
    total_gasto_mes double,
    estoque_disponivel double,
    lucro double,
    pizzas_disponiveis int
);

CREATE TABLE TbRelatorios (
	id int primary key auto_increment,
    total_gasto double,
    lucro_total double,
    quantidade_pizzas int,
    espaco_estoque_atualmente double,
	createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cod_estoque int,
    foreign key (cod_estoque) references tbEstoque(id)
);

ALTER TABLE tbItem ADD COLUMN cod_estoque INT;
ALTER TABLE tbItem ADD constraint FOREIGN KEY (cod_estoque) REFERENCES  tbEstoque(id);
ALTER TABLE tbSessions ADD FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);

CREATE TABLE tbHistorico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codItem INT,
    quantidade INT NOT NULL,
    data_movimentacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (codItem) REFERENCES tbItem(codItem)
);

DELIMITER //

CREATE TRIGGER atualizar_estoque_depois_de_modificacao
AFTER UPDATE ON tbItem
FOR EACH ROW
BEGIN
    INSERT INTO tbHistoricoEstoque (codItem, quantidade)
    VALUES (NEW.codItem, NEW.quantidade_ocup);
END;
//

DELIMITER ;

INSERT INTO TbRelatorios (total_gasto, lucro_total, quantidade_pizzas, espaco_estoque_atualmente, cod_estoque)
VALUES (500.00, 650.00, 20, 15.5, 1);
