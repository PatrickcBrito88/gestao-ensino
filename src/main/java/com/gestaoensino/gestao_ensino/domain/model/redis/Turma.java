package com.gestaoensino.gestao_ensino.domain.model.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash("GestaoEnsino_Turma")
public class Turma {

    @Id
    private Long id;
    private String nome;
    private String serie;

}
