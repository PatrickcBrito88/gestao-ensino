create table aluno (
    id bigint not null auto_increment,
    data_nascimento datetime,
    nome_completo varchar(255),
    telefone_responsavel varchar(255),
    turma_id bigint, primary key (id))
    engine=MyISAM