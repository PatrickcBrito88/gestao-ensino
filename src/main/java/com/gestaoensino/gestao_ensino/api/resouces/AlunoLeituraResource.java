package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.AlunoAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.response.AlunoResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/aluno")
public class AlunoLeituraResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;
    private final AlunoAssembler alunoAssembler;

    public AlunoLeituraResource(AlunoService alunoService, AlunoAssembler alunoAssembler) {
        this.alunoService = alunoService;
        this.alunoAssembler = alunoAssembler;
    }

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<List<AlunoResponseDTO>>> buscarTodosAlunos(){
        return retornarSucesso(alunoAssembler.montaListAlunoComIdDto(alunoService.listarAlunos()));
    }

    @GetMapping(value = "/buscar/{idAluno}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<AlunoResponseDTO>> buscarAluno(@PathVariable Long idAluno){
        return retornarSucesso(alunoAssembler.montaAlunoComIdDto(alunoService.buscarAluno(idAluno)));
    }
}
