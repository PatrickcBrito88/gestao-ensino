package com.gestaoensino.gestao_ensino.domain.repository.redis;

import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;
import org.springframework.data.repository.CrudRepository;

public interface EscolaRepository extends CrudRepository<Escola, String> {
}
