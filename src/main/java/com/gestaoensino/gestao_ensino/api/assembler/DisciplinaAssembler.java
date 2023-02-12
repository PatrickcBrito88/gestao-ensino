package com.gestaoensino.gestao_ensino.api.assembler;

import com.gestaoensino.gestao_ensino.api.dtos.DisciplinaDTO;
import com.gestaoensino.gestao_ensino.api.dtos.response.DisciplinaResponseDTO;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisciplinaAssembler {

    private ModelMapper modelMapper;

    public DisciplinaAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DisciplinaDTO montaDto (Disciplina disciplina){
        return modelMapper.map(disciplina, DisciplinaDTO.class);
    }
    public DisciplinaResponseDTO montaDisciplinaResponse (Disciplina disciplina){
        return modelMapper.map(disciplina, DisciplinaResponseDTO.class);
    }

    public Disciplina desmontaDto(DisciplinaDTO disciplinaDTO){
        return modelMapper.map(disciplinaDTO, Disciplina.class);
    }

    public Disciplina desmontaDisciplinaResponse(DisciplinaResponseDTO disciplinaResponseDTO){
        return modelMapper.map(disciplinaResponseDTO, Disciplina.class);
    }

    public List<DisciplinaDTO> montaListDto(List<Disciplina> listDisciplina){
        return listDisciplina.stream()
                .map(disciplina -> modelMapper.map(disciplina, DisciplinaDTO.class))
                .collect(Collectors.toList());
    }

    public List<DisciplinaResponseDTO> montaListaDisciplinaResponseDto(List<Disciplina> listDisciplina){
        return listDisciplina.stream()
                .map(disciplina -> modelMapper.map(disciplina, DisciplinaResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<Disciplina> desmontaListDto(List<DisciplinaDTO> listDisciplina){
        return listDisciplina.stream()
                .map(disciplinaDTO -> modelMapper.map(disciplinaDTO, Disciplina.class))
                .collect(Collectors.toList());
    }
}
