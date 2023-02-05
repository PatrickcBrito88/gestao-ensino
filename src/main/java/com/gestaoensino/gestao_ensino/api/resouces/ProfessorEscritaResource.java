package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDto;
import com.gestaoensino.gestao_ensino.domain.model.Professor;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.http.HttpStatus;
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
public class ProfessorEscritaResource {

    private final ProfessorService professorService;

    public ProfessorEscritaResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorDto salvarProfessor(@RequestBody ProfessorDto professorDto){
        return professorService.salvarProfessor(professorDto);
    }

    @PutMapping(value = "/atualizar/{idProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDto editarProfessor(@RequestBody Professor professor,
                                        @PathVariable Long idProfessor){
        return professorService.editarProfessor(professor, idProfessor);
    }

    @DeleteMapping(value = "/{idProfessor}")
    public String apagarProfessor(@PathVariable Long idProfessor){
        professorService.apagarProfessor(idProfessor);
        return "O professor foi deletado com sucesso!";
    }

}
