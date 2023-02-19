package com.gestaoensino.gestao_ensino.domain.repository;

import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import org.springframework.data.repository.CrudRepository;

public interface TurmaRepository extends CrudRepository<Turma, String> {
}
