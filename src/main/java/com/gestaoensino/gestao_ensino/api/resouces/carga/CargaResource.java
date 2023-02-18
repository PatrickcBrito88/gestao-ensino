package com.gestaoensino.gestao_ensino.api.resouces.carga;

import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/carga")
public class CargaResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final ProfessorService professorService;

    public CargaResource(AlunoService alunoService,
                         DisciplinaService disciplinaService,
                         ProfessorService professorService) {
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.professorService = professorService;
    }

//    @PostMapping
//    public ResponseEntity<RestResponseDTO<String>> carregarDados(){
//        Aluno aluno = new Aluno();
//        aluno.setNomeCompleto("Gabriel Almeida Dantas");
//        aluno.setDataNascimento(
//    }


}
