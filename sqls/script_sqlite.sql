
CREATE TABLE IF NOT EXISTS produtos(
    id INTEGER PRIMARY KEY, 
    nome TEXT NOT NULL, 
    descricao TEXT NOT NULL,
    valor REAL NOT NULL,
    quantidadeEstoque REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes (
  id INTEGER PRIMARY KEY,
  nome TEXT NOT NULL,
  cpf TEXT NOT NULL unique,
  email TEXT NOT NULL,
  telefone TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS vendas (
	id INTEGER PRIMARY KEY,
    dataHora INTEGER NULL,
	idCliente INT NOT NULL,
    total REAL not null,
    desconto REAL not null,
	CONSTRAINT vendas_FK_cliente FOREIGN KEY (idCliente) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS itensvenda (
	id INTEGER PRIMARY KEY,
    idVenda INT NOT NULL,
	idProduto INT NOT NULL,
    valor REAL NOT NULL,
    quantidade REAL NOT NULL,
	CONSTRAINT itensvenda_FK_venda FOREIGN KEY (idVenda) REFERENCES vendas(id),
    CONSTRAINT itensvenda_FK_produto FOREIGN KEY (idProduto) REFERENCES produtos(id)
    
);