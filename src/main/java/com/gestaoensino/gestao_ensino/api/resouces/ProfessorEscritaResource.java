package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.ProfessorAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> salvarProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        Professor professor = professorAssembler.desmontaDto(professorDTO);
        return retornarSucesso(professorAssembler.montaProfessorDto(professorService.salvarProfessor(professor)));
    }

    @PutMapping(value = "/atualizar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> editarProfessor(@RequestBody @Valid ProfessorDTO professorDTO,
                                                                         @PathVariable String idProfessor) {
        Professor professor = professorAssembler.desmontaDto(professorDTO);
        return retornarSucesso(professorAssembler.montaProfessorDto(professorService.editarProfessor(professor, idProfessor)));
    }

    @DeleteMapping(value = "/{idProfessor}")
    public ResponseEntity<RestResponseDTO<String>> apagarProfessor(@PathVariable String idProfessor) {
        professorService.apagarProfessor(idProfessor);
        return retornarSucesso("O professor foi deletado com sucesso!");
    }

}
