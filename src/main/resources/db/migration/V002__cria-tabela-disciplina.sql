create table disciplina (
    id bigint not null auto_increment,
    nome varchar(255),
    turma_id bigint,
    primary key (id))
    engine=MyISAM