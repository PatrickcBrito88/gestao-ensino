set foreign_key_checks = 0;

delete from aluno;
delete from disciplina;
delete from professor;

set foreign_key_checks = 1;

alter table aluno auto_increment = 1;
alter table disciplina auto_increment = 1;
alter table professor auto_increment = 1;

insert into aluno (id, nome_completo, telefone_responsavel, data_nascimento) values (1, 'Gabriel Brito', '22997361596', '2013-03-28');
insert into aluno (id, nome_completo, telefone_responsavel, data_nascimento) values (2, 'Miguel Brito', '21979791435', '2019-02-18');
insert into aluno (id, nome_completo, telefone_responsavel, data_nascimento) values (3, 'Enzo Goncalves', '2299735485', '2015-09-16');
insert into aluno (id, nome_completo, telefone_responsavel, data_nascimento) values (4, 'Joao Soares', '22978643215', '2016-04-05');


insert into disciplina (id, nome) values (1, 'Matemática');
insert into disciplina (id, nome) values (2, 'Portugues');
insert into disciplina (id, nome) values (3, 'Ciências');
insert into disciplina (id, nome) values (4, 'Biologia');
insert into disciplina (id, nome) values (5, 'Educação Física');


insert into professor (id, nome_completo, nome_comum, email, telefone) values (1, 'Thais Almeida', 'Thais', 'thais@email.com', '22988147885');
insert into professor (id, nome_completo, nome_comum, email, telefone) values (2, 'Ceil Carvalho', 'Ceil', 'ceil@email.com', '22988299874');
insert into professor (id, nome_completo, nome_comum, email, telefone) values (3, 'Patrick Brito', 'Patrick', 'patrick@email.com', '22997361596');
insert into professor (id, nome_completo, nome_comum, email, telefone) values (4, 'Altamir Brito', 'Altamir', 'altamir@email.com', '22988199332');
insert into professor (id, nome_completo, nome_comum, email, telefone) values (5, 'Elisane Gonçalves', 'Elisane', 'elisane@email.com', '2297985457');