package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.EscolaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;
import com.gestaoensino.gestao_ensino.services.EscolaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/escola")
public class EscolaEscritaResource extends GestaoEnsinoResource {

    private final EscolaService escolaService;
    private final ModelMapper modelMapper;
    public EscolaEscritaResource(EscolaService escolaService, ModelMapper modelMapper) {
        this.escolaService = escolaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<RestResponseDTO<EscolaDTO>> cadastrarEscola(@RequestBody EscolaDTO escolaDTO){
        Escola escola = modelMapper.map(escolaDTO, Escola.class);
        return retornarSucesso(modelMapper.map(escolaService.cadastrarEscola(escola), EscolaDTO.class));
    }

}
