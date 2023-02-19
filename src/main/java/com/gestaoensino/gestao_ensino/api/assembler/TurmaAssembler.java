package com.gestaoensino.gestao_ensino.api.assembler;

import com.gestaoensino.gestao_ensino.api.dtos.TurmaDTO;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TurmaAssembler {

    private ModelMapper modelMapper;

    public TurmaAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TurmaDTO montaDto (Turma turma){
        return modelMapper.map(turma, TurmaDTO.class);
    }

    public Turma desmontaDto(TurmaDTO turmaDTO){
        return modelMapper.map(turmaDTO, Turma.class);
    }

    public List<TurmaDTO> montaListDto(List<Turma> listTurma){
        return listTurma.stream()
                .map(turma -> modelMapper.map(turma, TurmaDTO.class))
                .collect(Collectors.toList());
    }

    public List<Turma> desmontaListDto(List<TurmaDTO> listTurma){
        return listTurma.stream()
                .map(turma -> modelMapper.map(turma, Turma.class))
                .collect(Collectors.toList());
    }
}
