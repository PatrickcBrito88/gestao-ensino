create table professor (
    id bigint not null auto_increment,
    email varchar(255),
    nome_completo varchar(255),
    nome_comum varchar(255),
    telefone varchar(255),
    primary key (id))
    engine=MyISAM