
CREATE TABLE IF NOT EXISTS produtos(
    id int AUTO_INCREMENT, 
    nome varchar(255) NOT NULL, 
    descricao varchar(255) NOT NULL,
    valor double NOT NULL,
    quantidadeEstoque double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clientes (
  id int AUTO_INCREMENT,
  nome varchar(255) NOT NULL,
  cpf varchar(11) NOT NULL unique,
  email varchar(100) NOT NULL,
  telefone varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vendas (
	id INT AUTO_INCREMENT,
    dataHora TIMESTAMP NULL,
	idCliente INT NOT NULL,
    total double not null,
    desconto double not null,
    PRIMARY KEY (id),
	CONSTRAINT vendas_FK_cliente FOREIGN KEY (idCliente) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS itensvenda (
	id INT AUTO_INCREMENT,
    idVenda INT NOT NULL,
	idProduto INT NOT NULL,
    valor double NOT NULL,
    quantidade double NOT NULL,
    PRIMARY KEY (id),
	CONSTRAINT itensvenda_FK_venda FOREIGN KEY (idVenda) REFERENCES vendas(id),
    CONSTRAINT itensvenda_FK_produto FOREIGN KEY (idProduto) REFERENCES produtos(id)
    
);