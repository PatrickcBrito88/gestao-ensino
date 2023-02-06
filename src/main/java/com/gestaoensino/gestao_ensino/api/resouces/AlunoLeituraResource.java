package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class AlunoLeituraResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;

    public AlunoLeituraResource(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<RestResponseDTO<List<AlunoDTO>>> buscarTodosAlunos(){
        return retornarSucesso(alunoService.listarAlunos());
    }

    @GetMapping(value = "/buscar/{idAluno}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<RestResponseDTO<AlunoDTO>> buscarAluno(@PathVariable Long idAluno){
        return retornarSucesso(alunoService.buscarAluno(idAluno));
    }
}
