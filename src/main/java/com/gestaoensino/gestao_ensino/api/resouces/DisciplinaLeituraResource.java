package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.DisciplinaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.response.DisciplinaResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/disciplina")
public class DisciplinaLeituraResource extends GestaoEnsinoResource {

    private final DisciplinaService disciplinaService;
    private final DisciplinaAssembler disciplinaAssembler;

    public DisciplinaLeituraResource(DisciplinaService disciplinaService, DisciplinaAssembler disciplinaAssembler) {
        this.disciplinaService = disciplinaService;
        this.disciplinaAssembler = disciplinaAssembler;
    }

    @GetMapping("/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<DisciplinaResponseDTO>> buscarDisciplina(@PathVariable Integer idDisciplina){
        return retornarSucesso(disciplinaAssembler.montaDisciplinaResponse(disciplinaService.buscarDisciplina(idDisciplina)));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO<List<DisciplinaResponseDTO>>> listarDisciplinas(){
        return retornarSucesso(disciplinaAssembler.montaListaDisciplinaResponseDto(disciplinaService.listarDisciplinas()));
    }
}
