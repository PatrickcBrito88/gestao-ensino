package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDto;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/professor")
public class ProfessorLeituraResource {

    private final ProfessorService professorService;

    public ProfessorLeituraResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    private List<ProfessorDto> buscarTodosProfessores(){
        return professorService.listarProfessores();
    }

    @GetMapping(value = "/buscar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    private ProfessorDto buscarTodosProfessores(@PathVariable Long idProfessor){
        return professorService.buscarProfessor(idProfessor);
    }


}
