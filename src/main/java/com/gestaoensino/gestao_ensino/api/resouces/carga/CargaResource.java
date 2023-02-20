package com.gestaoensino.gestao_ensino.api.resouces.carga;

import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import com.gestaoensino.gestao_ensino.domain.model.redis.Escola;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.services.EscolaService;
import com.gestaoensino.gestao_ensino.services.ProfessorService;
import com.gestaoensino.gestao_ensino.services.TurmaService;
import com.gestaoensino.gestao_ensino.utils.LeitorJsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/carga")
public class CargaResource extends GestaoEnsinoResource {

    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final ProfessorService professorService;
    private final TurmaService turmaService;
    private final EscolaService escolaService;

    public CargaResource(AlunoService alunoService,
                         DisciplinaService disciplinaService,
                         ProfessorService professorService,
                         TurmaService turmaService,
                         EscolaService escolaService) {
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.professorService = professorService;
        this.turmaService = turmaService;
        this.escolaService = escolaService;
    }

    @PostMapping("/carga-completa")
    public ResponseEntity<RestResponseDTO<String>> cargaCompleta() {
        cargaAluno();
        cargaDisciplia();
        cargaProfessor();
        cargaTurma();
        cargaEscola();
        return (retornarSucesso("Carga completa realizada com sucesso"));
    }

    private void cargaAluno() {
        String MOCK_FOLDER_ALUNO = "/carga/aluno";
        String ALUNO1_JSON = "aluno1.json";
        String ALUNO2_JSON = "aluno2.json";
        String ALUNO3_JSON = "aluno3.json";
        Aluno aluno1 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO1_JSON, Aluno.class);
        Aluno aluno2 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO2_JSON, Aluno.class);
        Aluno aluno3 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO3_JSON, Aluno.class);
        alunoService.salvarAluno(aluno1);
        alunoService.salvarAluno(aluno2);
        alunoService.salvarAluno(aluno3);
    }

    private void cargaDisciplia() {
        String MOCK_FOLDER_DISCIPLINA = "/carga/disciplina";
        String DISCIPLINA1_JSON = "disciplina1.json";
        String DISCIPLINA2_JSON = "disciplina2.json";
        String DISCIPLINA3_JSON = "disciplina3.json";
        Disciplina disciplina1 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA1_JSON, Disciplina.class);
        Disciplina disciplina2 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA2_JSON, Disciplina.class);
        Disciplina disciplina3 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA3_JSON, Disciplina.class);
        disciplinaService.salvarDisciplina(disciplina1);
        disciplinaService.salvarDisciplina(disciplina2);
        disciplinaService.salvarDisciplina(disciplina3);
    }

    private void cargaProfessor() {
        String MOCK_FOLDER_PROFESSOR = "/carga/professor";
        String PROFESSOR1_JSON = "professor1.json";
        String PROFESSOR2_JSON = "professor2.json";
        String PROFESSOR3_JSON = "professor3.json";
        Professor professor1 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR1_JSON, Professor.class);
        Professor professor2 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR2_JSON, Professor.class);
        Professor professor3 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR3_JSON, Professor.class);
        professorService.salvarProfessor(professor1);
        professorService.salvarProfessor(professor2);
        professorService.salvarProfessor(professor3);
    }

    private void cargaTurma() {
        String MOCK_FOLDER_TURMA = "/carga/turma";
        String TURMA1_JSON = "turma1.json";
        String TURMA2_JSON = "turma2.json";
        String TURMA3_JSON = "turma3.json";
        Turma turma1 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA1_JSON, Turma.class);
        Turma turma2 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA2_JSON, Turma.class);
        Turma turma3 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA3_JSON, Turma.class);
        turmaService.cadastrarTurma(turma1);
        turmaService.cadastrarTurma(turma2);
        turmaService.cadastrarTurma(turma3);
    }

    private void cargaEscola() {
        String MOCK_FOLDER_ESCOLA = "/carga/escola";
        String ESCOLA1_JSON = "escola1.json";
        String ESCOLA2_JSON = "escola2.json";
        String ESCOLA3_JSON = "escola3.json";
        Escola escola1 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ESCOLA, ESCOLA1_JSON, Escola.class);
        Escola escola2 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ESCOLA, ESCOLA2_JSON, Escola.class);
        Escola escola3 = LeitorJsonUtils.getMockObject(MOCK_FOLDER_ESCOLA, ESCOLA3_JSON, Escola.class);
        escolaService.cadastrarEscola(escola1);
        escolaService.cadastrarEscola(escola2);
        escolaService.cadastrarEscola(escola3);
    }


}
