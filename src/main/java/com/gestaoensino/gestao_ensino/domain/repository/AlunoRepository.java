package com.gestaoensino.gestao_ensino.domain.repository;

import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, String> {
}
