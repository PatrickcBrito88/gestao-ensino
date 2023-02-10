package com.gestaoensino.gestao_ensino.services.implementation;

import com.gestaoensino.gestao_ensino.api.assembler.TurmaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.TurmaDTO;
import com.gestaoensino.gestao_ensino.domain.exception.TurmaNaoEncontradaException;
import com.gestaoensino.gestao_ensino.domain.model.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.Disciplina;
import com.gestaoensino.gestao_ensino.domain.model.Turma;
import com.gestaoensino.gestao_ensino.domain.repository.TurmaRepository;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.services.TurmaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements TurmaService {

    private final TurmaRepository turmaRepository;
    private final ModelMapper modelMapper;
    private final DisciplinaService disciplinaService;
    private final TurmaAssembler turmaAssembler;
    private final AlunoService alunoService;

    public TurmaServiceImpl(TurmaRepository turmaRepository,
                            ModelMapper modelMapper,
                            DisciplinaService disciplinaService,
                            TurmaAssembler turmaAssembler,
                            AlunoService alunoService) {
        this.turmaRepository = turmaRepository;
        this.modelMapper = modelMapper;
        this.disciplinaService = disciplinaService;
        this.turmaAssembler = turmaAssembler;
        this.alunoService = alunoService;
    }

    private Turma buscarOuFalhar(Long id){
        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNaoEncontradaException(id));
    }

    @Override
    @Transactional
    public Turma cadastrarTurma(TurmaDTO turmaDTO) {
        return turmaRepository.save(turmaAssembler.desmontaDto(turmaDTO));
    }

    @Override
    public Turma buscarTurma(Long idTurma) {
        return buscarOuFalhar(idTurma);
    }

    @Override
    @Transactional
    public void adicionarDisciplina(Long idDisciplina, Long idTurma) {
        Turma turma = buscarOuFalhar(idTurma);
        Disciplina disciplina = modelMapper.map(disciplinaService.buscarDisciplina(idDisciplina), Disciplina.class);
        turma.adicionaDisciplina(disciplina);
    }

    @Override
    @Transactional
    public void adicionarAluno(Long idAluno, Long idTurma) {
        Turma turma = buscarOuFalhar(idTurma);
        Aluno aluno = modelMapper.map(alunoService.buscarAluno(idAluno), Aluno.class);
        turma.adicionaAluno(aluno);
    }

    @Override
    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    @Override
    @Transactional
    public void removerDisciplina(Long idDisciplina, Long idTurma) {
        Turma turma = buscarOuFalhar(idTurma);
        Disciplina disciplina = modelMapper.map(disciplinaService.buscarDisciplina(idDisciplina), Disciplina.class);
        turma.removeDisciplina(disciplina);
    }

    @Override
    @Transactional
    public void removerAluno(Long idAluno, Long idTurma) {
        Turma turma = buscarOuFalhar(idTurma);
        Aluno aluno = modelMapper.map(alunoService.buscarAluno(idAluno), Aluno.class);
        turma.removeAluno(aluno);
    }

    @Override
    public void apagarTurma(Long id) {
        //Fazer depois da criação do ano letivo pois vai ter que fazer o http status conflict
    }

    @Override
    @Transactional
    public Turma editarDisciplina(String nome, Long id) {
        Turma turma = buscarOuFalhar(id);
        turma.setNome(nome);
        return turmaRepository.save(turma);
    }
}
