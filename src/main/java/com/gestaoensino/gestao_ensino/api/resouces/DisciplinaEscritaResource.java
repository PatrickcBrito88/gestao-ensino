package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.DisciplinaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sistema-gestao-ensino/disciplina")
public class DisciplinaEscritaResource extends GestaoEnsinoResource {

    private final DisciplinaService disciplinaService;
    private final DisciplinaAssembler disciplinaAssembler;

    public DisciplinaEscritaResource(DisciplinaService disciplinaService, DisciplinaAssembler disciplinaAssembler) {
        this.disciplinaService = disciplinaService;
        this.disciplinaAssembler = disciplinaAssembler;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<RestResponseDTO<DisciplinaDTO>> salvarDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO){
        Disciplina disciplina = disciplinaAssembler.desmontaDto(disciplinaDTO);
        return retornarSucesso(disciplinaAssembler.montaDto(disciplinaService.salvarDisciplina(disciplina)));
    }

    @PutMapping(value = "/atualizar/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<DisciplinaDTO>> editarDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO,
                                                                            @PathVariable String idDisciplina){
        Disciplina disciplina = disciplinaAssembler.desmontaDto(disciplinaDTO);
        return retornarSucesso(disciplinaAssembler.montaDto(disciplinaService.editarDisciplina(disciplina, idDisciplina)));
    }

    @DeleteMapping(value = "/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<String>> apagarDisciplina(@PathVariable String idDisciplina){
        disciplinaService.apagarDisciplina(idDisciplina);
        return retornarSucesso("Disciplina deletada com sucesso!");
    }
}
