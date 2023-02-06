package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sistema-gestao-ensino/disciplina")
public class DisciplinaEscritaResource extends GestaoEnsinoResource {

    private final DisciplinaService disciplinaService;

    public DisciplinaEscritaResource(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<RestResponseDTO<DisciplinaDTO>> salvarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO){
        return retornarSucesso(disciplinaService.salvarDisciplina(disciplinaDTO));
    }

    @PutMapping(value = "/atualizar/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<DisciplinaDTO>> editarDisciplina(@RequestBody Disciplina disciplina,
                                                                            @PathVariable Long idDisciplina){
        return retornarSucesso(disciplinaService.editarDisciplina(disciplina, idDisciplina));
    }

    @DeleteMapping(value = "/{idDisciplina}")
    public ResponseEntity<RestResponseDTO<String>> apagarDisciplina(@PathVariable Long idDisciplina){
        disciplinaService.apagarDisciplina(idDisciplina);
        return retornarSucesso("Disciplina apagada com sucesso!");
    }
}
