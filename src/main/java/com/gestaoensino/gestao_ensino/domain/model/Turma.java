package com.gestaoensino.gestao_ensino.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "turma")
    @ToString.Exclude
    private Set<Disciplina> disciplinas = new HashSet<>();
    @OneToMany(mappedBy = "turma")
    @ToString.Exclude
    private Set<Aluno> alunos = new HashSet<>();
    private String nome;
    private String serie;

    public boolean adicionaDisciplina(Disciplina disciplina){
        return disciplinas.add(disciplina);
    }

    public boolean removeDisciplina(Disciplina disciplina){
        return disciplinas.remove(disciplina);
    }

    public boolean adicionaAluno(Aluno aluno){
        return alunos.add(aluno);
    }

    public boolean removeAluno(Aluno aluno){
        return alunos.remove(aluno);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Turma turma = (Turma) o;
        return id != null && Objects.equals(id, turma.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
