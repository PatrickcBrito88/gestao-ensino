package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.ProfessorAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.Professor;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class ProfessorEscritaResource extends GestaoEnsinoResource {

    private final ProfessorService professorService;
    private final ProfessorAssembler professorAssembler;

    public ProfessorEscritaResource(ProfessorService professorService, ProfessorAssembler professorAssembler) {
        this.professorService = professorService;
        this.professorAssembler = professorAssembler;
    }

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> salvarProfessor(@RequestBody Professor professor){
        return retornarSucesso(professorAssembler.montaProfessorDto(professorService.salvarProfessor(professor)));
    }

    @PutMapping(value = "/atualizar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> editarProfessor(@RequestBody Professor professor,
                                        @PathVariable Long idProfessor){
        return retornarSucesso(professorAssembler.montaProfessorDto(professorService.editarProfessor(professor, idProfessor)));
    }

    @DeleteMapping(value = "/{idProfessor}")
    public ResponseEntity<RestResponseDTO<String>> apagarProfessor(@PathVariable Long idProfessor){
        professorService.apagarProfessor(idProfessor);
        return retornarSucesso("O professor foi deletado com sucesso!");
    }

}
