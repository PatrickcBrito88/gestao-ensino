package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.input.EscolaInput;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.EscolaCorrente;
import com.gestaoensino.gestao_ensino.services.EscolaCorrenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/turma-dynamo")
public class TurmaDynamoEscritaResource extends GestaoEnsinoResource {

    private final EscolaCorrenteService turmaDynamoService;

    public TurmaDynamoEscritaResource(EscolaCorrenteService turmaDynamoService) {
        this.turmaDynamoService = turmaDynamoService;
    }

    @PostMapping("/salvar-turma")
    public ResponseEntity<RestResponseDTO<EscolaCorrente>> salvarTurma(@RequestBody EscolaInput escolaInput){
        //TODO  criar o DTO para retornar a escola
        return retornarSucesso(turmaDynamoService.salvar(escolaInput));
    }
}
