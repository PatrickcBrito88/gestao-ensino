package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.dtos.input.TurmaDynamoInput;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;
import com.gestaoensino.gestao_ensino.domain.repository.dynamo.TurmaDynamoRepositoryImpl;
import com.gestaoensino.gestao_ensino.services.TurmaDynamoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TurmaDynamoServiceImpl implements TurmaDynamoService {

    private final ModelMapper modelMapper;
    private final TurmaDynamoRepositoryImpl turmaDynamoRepository;

    public TurmaDynamoServiceImpl(ModelMapper modelMapper, TurmaDynamoRepositoryImpl turmaDynamoRepository) {
        this.modelMapper = modelMapper;
        this.turmaDynamoRepository = turmaDynamoRepository;
    }

    @Override
    public TurmaDynamo salvarTurma(TurmaDynamoInput turmaDynamoInput) {
        TurmaDynamo turmaDynamo = modelMapper.map(turmaDynamoInput, TurmaDynamo.class);
        return turmaDynamoRepository.save(turmaDynamo);
    }
}
