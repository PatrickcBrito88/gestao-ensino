alter table aluno add constraint aluno_turmaFK
    foreign key (turma_id) references turma (id)