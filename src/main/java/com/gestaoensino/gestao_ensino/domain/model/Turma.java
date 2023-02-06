package com.gestaoensino.gestao_ensino.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Disciplina> disciplinas = new ArrayList<>();
    @OneToMany(mappedBy = "turma")
    @ToString.Exclude
    private List<Aluno> alunos = new ArrayList<>();
    private String nome;
    private String serie;

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
