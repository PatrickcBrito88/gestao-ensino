package com.gestaoensino.gestao_ensino.domain.repository;

import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import org.springframework.data.repository.CrudRepository;

public interface DisciplinaRepository extends CrudRepository<Disciplina, String> {
}
