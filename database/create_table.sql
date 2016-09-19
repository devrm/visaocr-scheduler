#drop table tbl_dados_nota;
create table tbl_dados_nota (
	id integer auto_increment primary key,
    caminho_imagem varchar(1000),    
    resultado_analise varchar(21845),
    valor_nota varchar(10),
    data_nota varchar(20),
    coo varchar(10),
    cnpj varchar(20),
    status integer
);