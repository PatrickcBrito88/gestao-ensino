package com.gestaoensino.gestao_ensino.api.assembler;

import com.gestaoensino.gestao_ensino.api.dtos.AlunoDTO;
import com.gestaoensino.gestao_ensino.api.dtos.com_id.AlunoComIdDTO;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlunoAssembler {

    private ModelMapper modelMapper;

    public AlunoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AlunoDTO montaAlunoDto(Aluno aluno){
        return modelMapper.map(aluno, AlunoDTO.class);
    }
    public AlunoComIdDTO montaAlunoComIdDto(Aluno aluno){
        return modelMapper.map(aluno, AlunoComIdDTO.class);
    }

    public Aluno desmontaDto(AlunoDTO alunoDTO){
        return modelMapper.map(alunoDTO, Aluno.class);
    }

    public List<AlunoDTO> montaListAlunoDto(List<Aluno> listAluno){
        return listAluno.stream()
                .map(turma -> modelMapper.map(turma, AlunoDTO.class))
                .collect(Collectors.toList());
    }

    public List<AlunoComIdDTO> montaListAlunoComIdDto(List<Aluno> listAluno){
        return listAluno.stream()
                .map(turma -> modelMapper.map(turma, AlunoComIdDTO.class))
                .collect(Collectors.toList());
    }

    public List<Aluno> desmontaListDto(List<AlunoDTO> listAluno){
        return listAluno.stream()
                .map(turma -> modelMapper.map(turma, Aluno.class))
                .collect(Collectors.toList());
    }
}
