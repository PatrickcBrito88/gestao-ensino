package com.gestaoensino.gestao_ensino.domain.model.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("GestaoEnsino_Turma")
public class Turma {

    @Id
    private Long id;
    private String nome;
    private String serie;

}
