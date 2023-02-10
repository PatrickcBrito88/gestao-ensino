package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.AlunoAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/aluno")
public class AlunoEscritaResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;
    private final AlunoAssembler alunoAssembler;

    public AlunoEscritaResource(AlunoService alunoService, AlunoAssembler alunoAssembler) {
        this.alunoService = alunoService;
        this.alunoAssembler = alunoAssembler;
    }

    @PostMapping(value = "/salvar")
    private ResponseEntity<RestResponseDTO<AlunoDTO>> salvarAluno(@RequestBody AlunoDTO alunoDto){
        return retornarSucesso(alunoAssembler.montaDto(alunoService.salvarAluno(alunoDto)));
    }

    @PutMapping(value = "/atualizar/{idAluno}")
    private ResponseEntity<RestResponseDTO<AlunoDTO>> editarAluno(@RequestBody Aluno aluno,
                                 @PathVariable Long idAluno){
        return retornarSucesso(alunoAssembler.montaDto(alunoService.editarAluno(aluno, idAluno)));
    }

    @DeleteMapping(value = "/{idAluno}")
    public ResponseEntity<RestResponseDTO<String>> apagarAluno(@PathVariable Long idAluno){
        alunoService.apagarAluno(idAluno);
        return retornarSucesso("O aluno foi deletado com sucesso!");
    }

}
