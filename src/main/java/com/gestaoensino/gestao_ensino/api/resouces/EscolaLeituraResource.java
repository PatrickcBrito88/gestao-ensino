package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.dtos.EscolaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.services.EscolaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/escola")
public class EscolaLeituraResource extends GestaoEnsinoResource {

    private final EscolaService escolaService;
    private final ModelMapper modelMapper;
    public EscolaLeituraResource(EscolaService escolaService, ModelMapper modelMapper) {
        this.escolaService = escolaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO<List<EscolaDTO>>> listarEscola(){
        return retornarSucesso(escolaService.listarEscolas().stream().map(escola -> modelMapper.map(escola, EscolaDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{idEscola}")
    public ResponseEntity<RestResponseDTO<EscolaDTO>> listarEscola(@PathVariable Integer idEscola){
        return retornarSucesso(modelMapper.map(escolaService.buscarEscola(idEscola), EscolaDTO.class));
    }

}
