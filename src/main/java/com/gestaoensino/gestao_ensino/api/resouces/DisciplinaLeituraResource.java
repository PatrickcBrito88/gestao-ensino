package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
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

    public DisciplinaLeituraResource(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping("/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<DisciplinaDTO>> buscarDisciplina(@PathVariable Long idDisciplina){
            return retornarSucesso(disciplinaService.buscarDisciplina(idDisciplina));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO<List<DisciplinaDTO>>> listarDisciplinas(){
        return retornarSucesso(disciplinaService.listarDisciplinas());
    }
}
