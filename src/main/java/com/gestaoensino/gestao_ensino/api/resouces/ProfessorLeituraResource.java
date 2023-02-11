package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.ProfessorAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.ProfessorComIdDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class ProfessorLeituraResource extends GestaoEnsinoResource {

    private final ProfessorService professorService;
    private final ProfessorAssembler professorAssembler;

    public ProfessorLeituraResource(ProfessorService professorService,
                                    ProfessorAssembler professorAssembler) {
        this.professorService = professorService;
        this.professorAssembler = professorAssembler;
    }

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<List<ProfessorComIdDTO>>> buscarTodosProfessores(){
        return retornarSucesso(professorAssembler.montaListProfessorComIdDto(professorService.listarProfessores()));
    }

    @GetMapping(value = "/buscar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<ProfessorComIdDTO>> buscarProfessor(@PathVariable Long idProfessor){
        return retornarSucesso(professorAssembler.montaProfessorIdDto(professorService.buscarProfessor(idProfessor)));
    }


}
