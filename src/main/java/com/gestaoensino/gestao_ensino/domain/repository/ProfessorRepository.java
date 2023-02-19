package com.gestaoensino.gestao_ensino.domain.repository;

import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {
}
