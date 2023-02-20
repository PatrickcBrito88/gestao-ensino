package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.input.TurmaDynamoInput;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;
import com.gestaoensino.gestao_ensino.domain.repository.dynamo.TurmaDynamoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/turma-dynamo")
public class TurmaDynamoEscritaResource extends GestaoEnsinoResource {

    private TurmaDynamoRepository turmaDynamoRepository;

    public TurmaDynamoEscritaResource(TurmaDynamoRepository turmaDynamoRepository) {
        this.turmaDynamoRepository = turmaDynamoRepository;
    }

    @PostMapping("/salvar-turma")
    public ResponseEntity<RestResponseDTO<TurmaDynamo>> salvarTurma(@RequestBody TurmaDynamoInput turmaDynamoInput){

        return retornarSucesso(turmaDynamoRepository.salvar(turmaDynamoInput));
    }
}
