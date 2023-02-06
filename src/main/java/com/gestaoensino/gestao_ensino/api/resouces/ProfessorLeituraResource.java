package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class ProfessorLeituraResource extends GestaoEnsinoResource {

    private final ProfessorService professorService;

    public ProfessorLeituraResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<List<ProfessorDTO>>> buscarTodosProfessores(){
        return retornarSucesso(professorService.listarProfessores());
    }

    @GetMapping(value = "/buscar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponseDTO<ProfessorDTO>> buscarProfessor(@PathVariable Long idProfessor){
        return retornarSucesso(professorService.buscarProfessor(idProfessor));
    }


}
