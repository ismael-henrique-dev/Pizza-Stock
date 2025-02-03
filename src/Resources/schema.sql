create database javaConnectionTeste;

use javaConnectionTeste;

create table tbAdmin (
	id int primary key auto_increment,
    nome varchar(50),
    login varchar(50), # tirar esse atributo
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

# excluir depois, não há necessidade
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

#alterei aqui
ALTER TABLE tbItem ADD COLUMN idAdmin INT;
ALTER TABLE tbItem ADD constraint FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);

ALTER TABLE tbSessions ADD FOREIGN KEY (idAdmin) REFERENCES tbAdmin(id);

DROP TRIGGER IF EXISTS atualizar_estoque_depois_de_modificacao;

ALTER TABLE tbEstoque ADD COLUMN total_vendas INT DEFAULT 0;

ALTER TABLE tbHistorico ADD COLUMN id_relatorio INT;
ALTER TABLE tbHistorico ADD FOREIGN KEY (id_relatorio) REFERENCES TbRelatorios(id);

ALTER TABLE TbRelatorios ADD COLUMN periodo_inicio TIMESTAMP;

# delete from tbSessions;

# update tbItem set idAdmin = 1 where codItem = 5;

# select * from tbItem where idAdmin = 1;