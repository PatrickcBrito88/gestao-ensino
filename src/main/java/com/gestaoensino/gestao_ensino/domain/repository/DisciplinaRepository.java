package com.gestaoensino.gestao_ensino.domain.repository;

import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
