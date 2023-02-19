package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.TurmaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.TurmaDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.TurmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/turma")
public class TurmaLeituraResource extends GestaoEnsinoResource {

    private final TurmaService turmaService;
    private final TurmaAssembler turmaAssembler;

    public TurmaLeituraResource(TurmaService turmaService,
                                TurmaAssembler turmaAssembler) {
        this.turmaService = turmaService;
        this.turmaAssembler = turmaAssembler;
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO<List<TurmaDTO>>> listarTurmas(){
        return retornarSucesso(turmaAssembler.montaListDto(turmaService.listarTurmas()));
    }

}
