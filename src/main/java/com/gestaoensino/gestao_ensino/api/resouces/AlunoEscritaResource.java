package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/aluno")
public class AlunoEscritaResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;

    public AlunoEscritaResource(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<RestResponseDTO<AlunoDTO>> salvarAluno(@RequestBody AlunoDTO alunoDto){
        return retornarSucesso(alunoService.salvarAluno(alunoDto));
    }

    @PutMapping(value = "/atualizar/{idAluno}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<RestResponseDTO<AlunoDTO>> editarAluno(@RequestBody Aluno aluno,
                                 @PathVariable Long idAluno){
        return retornarSucesso(alunoService.editarAluno(aluno, idAluno));
    }

    @DeleteMapping(value = "/{idAluno}")
    public ResponseEntity<RestResponseDTO<String>> apagarAluno(@PathVariable Long idAluno){
        alunoService.apagarAluno(idAluno);
        return retornarSucesso("O aluno foi deletado com sucesso!");
    }

}
