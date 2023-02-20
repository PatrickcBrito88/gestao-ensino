package com.gestaoensino.gestao_ensino.domain.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gestaoensino.gestao_ensino.api.dtos.input.DisciplinaInput;
import com.gestaoensino.gestao_ensino.api.dtos.input.TurmaDynamoInput;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.AlunoDynamo;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.AvaliacoesDynamo;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.DisciplinaDynamo;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.ProfessorDynamo;
import com.gestaoensino.gestao_ensino.domain.model.dynamo.TurmaDynamo;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import com.gestaoensino.gestao_ensino.services.TurmaDynamoService;
import com.gestaoensino.gestao_ensino.services.TurmaService;
import org.apache.commons.math3.util.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TurmaDynamoRepositoryImpl implements TurmaDynamoService {

    private final DynamoDBMapper dynamoDBMapper;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final ProfessorService professorService;
    private final TurmaService turmaService;
    private final ModelMapper modelMapper;

    public TurmaDynamoRepositoryImpl(DynamoDBMapper dynamoDBMapper,
                                     AlunoService alunoService,
                                     DisciplinaService disciplinaService,
                                     ProfessorService professorService,
                                     TurmaService turmaService,
                                     ModelMapper modelMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.professorService = professorService;
        this.turmaService = turmaService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurmaDynamo salvarTurma(TurmaDynamoInput turmaDynamoInput) {
        TurmaDynamo turmaDynamo = extraiDadosTurmaDynamo(turmaDynamoInput);
        dynamoDBMapper.save(turmaDynamo);
        return turmaDynamo;
    }

    private TurmaDynamo extraiDadosTurmaDynamo(TurmaDynamoInput turmaDynamoInput) {
        Turma turma = turmaService.buscarTurma(turmaDynamoInput.getId());
        TurmaDynamo turmaDynamo = new TurmaDynamo();
        BeanUtils.copyProperties(turma, turmaDynamo);
        turmaDynamo.setAnoLetivo(turmaDynamoInput.getAnoLetivo());

        List<Aluno> alunos = turmaDynamoInput.getAlunos().stream()
                .map(alunoInput -> alunoService.buscarAluno(alunoInput.getId()))
                .collect(Collectors.toList());

        List<AlunoDynamo> alunosFormatados = alunos.stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDynamo.class))
                .collect(Collectors.toList());

        turmaDynamoInput.getAlunos().stream()
                .peek(alunoInput -> dataExtractor(Pair.create(alunoInput.getId(), alunoInput.getDisciplinas()), alunosFormatados))
                .collect(Collectors.toList());


        turmaDynamo.setAlunos(alunosFormatados);

        return turmaDynamo;
    }

    private void dataExtractor(Pair<Integer, List<DisciplinaInput>> transfer, List<AlunoDynamo> alunosFormatados) {
        List<DisciplinaDynamo> disciplinaFormatada = transfer.getSecond().stream()
                .map(disciplinaInput -> modelMapper.map(disciplinaInput, DisciplinaDynamo.class))
                .peek(disciplinaDynamo -> disciplinaDynamo.setAvaliacoes(disciplinaDynamo.getAvaliacoes().stream().map(avaliacao -> modelMapper.map(avaliacao, AvaliacoesDynamo.class)).collect(Collectors.toList())))
                .peek(this::geraCopiaDisciplina)
                .peek(disciplinaDynamo -> disciplinaDynamo.setProfessor(modelMapper.map(professorService.buscarProfessor(disciplinaDynamo.getId()), ProfessorDynamo.class)))
                .collect(Collectors.toList());

        alunosFormatados.stream()
                .filter(alunoDynamo -> alunoDynamo.getId().equals(transfer.getFirst()))
                .peek(alunoDynamo -> alunoDynamo.setDisciplinas(disciplinaFormatada))
                .collect(Collectors.toList());
    }

    private void geraCopiaDisciplina(DisciplinaDynamo disciplinaDynamo){
        DisciplinaDynamo copia = modelMapper.map(disciplinaService.buscarDisciplina(disciplinaDynamo.getId()), DisciplinaDynamo.class);
        BeanUtils.copyProperties(copia, disciplinaDynamo, "avaliacoes");
    }


}
