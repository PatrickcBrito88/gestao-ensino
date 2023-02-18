alter table disciplina add constraint disciplina_turmaFK
    foreign key (turma_id) references turma (id)