package com.gestaoensino.gestao_ensino.domain.model.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@RedisHash("GestaoEnsino_Aluno")
public class Aluno {

    @Id
    private String id;
    @Indexed
    private String nomeCompleto;
    private String telefoneResponsavel;
    @Indexed
    private Date dataNascimento;

}
