#drop table tbl_dados_nota;
create table tbl_dados_nota (
	id integer auto_increment primary key,
    caminho_imagem varchar(255),
    base64 varchar(4000),
    resultado_analise varchar(255),
    status integer
);