package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.Professor;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class ProfessorEscritaResource extends GestaoEnsinoResource {

    private final ProfessorService professorService;

    public ProfessorEscritaResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> salvarProfessor(@RequestBody ProfessorDTO professorDto){
        return retornarSucesso(professorService.salvarProfessor(professorDto));
    }

    @PutMapping(value = "/atualizar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> editarProfessor(@RequestBody Professor professor,
                                        @PathVariable Long idProfessor){
        return retornarSucesso(professorService.editarProfessor(professor, idProfessor));
    }

    @DeleteMapping(value = "/{idProfessor}")
    public ResponseEntity<RestResponseDTO<String>> apagarProfessor(@PathVariable Long idProfessor){
        professorService.apagarProfessor(idProfessor);
        return retornarSucesso("O professor foi deletado com sucesso!");
    }

}
