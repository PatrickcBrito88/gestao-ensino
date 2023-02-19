package com.gestaoensino.gestao_ensino.domain.model.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("GestaoEnsino_Professor")
public class Professor {

    @Id
    private Long id;
    private String nomeCompleto;
    private String nomeComum;
    private String email;
    private String telefone;
}
