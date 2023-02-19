package com.gestaoensino.gestao_ensino.api.assembler;

import com.gestaoensino.gestao_ensino.api.dtos.ProfessorDTO;
import com.gestaoensino.gestao_ensino.api.dtos.response.ProfessorResponseDTO;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfessorAssembler {

    private ModelMapper modelMapper;

    public ProfessorAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProfessorDTO montaProfessorDto(Professor professor){
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    public ProfessorResponseDTO montaProfessorIdDto(Professor professor){
        return modelMapper.map(professor, ProfessorResponseDTO.class);
    }

    public Professor desmontaDto(ProfessorDTO professorDto){
        return modelMapper.map(professorDto, Professor.class);
    }

    public List<ProfessorDTO> montaListProfessorDto(List<Professor> listProfessor){
        return listProfessor.stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProfessorResponseDTO> montaListProfessorComIdDto(List<Professor> listProfessor){
        return listProfessor.stream()
                .map(professor -> modelMapper.map(professor, ProfessorResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<Professor> desmontaListDto(List<ProfessorDTO> listProfessor){
        return listProfessor.stream()
                .map(professor -> modelMapper.map(professor, Professor.class))
                .collect(Collectors.toList());
    }

}
