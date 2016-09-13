#drop table tbl_dados_nota;
create table tbl_dados_nota (
	id integer auto_increment primary key,
    caminho_imagem varchar(1000),    
    resultado_analise varchar(4000),
    valor_nota double,
    data_nota date,
    cnpj varchar(20),
    status integer
);