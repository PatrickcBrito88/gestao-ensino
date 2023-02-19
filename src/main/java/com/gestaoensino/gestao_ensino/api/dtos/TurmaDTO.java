package com.gestaoensino.gestao_ensino.api.dtos;

import com.gestaoensino.gestao_ensino.domain.enums.ESerie;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TurmaDTO {

//    @Size(min = 1, message = "{turma.quantidade.minima.disciplinas}")
//    private Set<Long> idDisciplinas = new HashSet<>();
//    @Size(min = 1, message = "{turma.quantidade.minima.alunos}")
//    private Set<Long> idAlunos = new HashSet<>();
    @NotBlank(message = "{turma.nome.vazio}")
    private String nome;
    @NotBlank(message = "{turma.serie.vazio}")
    private ESerie serie;

}
