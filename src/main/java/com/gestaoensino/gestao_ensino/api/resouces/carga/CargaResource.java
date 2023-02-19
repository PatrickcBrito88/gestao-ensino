package com.gestaoensino.gestao_ensino.api.resouces.carga;

import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Aluno;
import com.gestaoensino.gestao_ensino.domain.model.redis.Disciplina;
import com.gestaoensino.gestao_ensino.domain.model.redis.Professor;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
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

    /*
    FOLDERS
     */
    protected static final String MOCK_FOLDER_ALUNO = "/carga/aluno";
    protected static final String MOCK_FOLDER_DISCIPLINA = "/carga/disciplina";
    protected static final String MOCK_FOLDER_PROFESSOR = "/carga/professor";
    protected static final String MOCK_FOLDER_TURMA = "/carga/turma";

    /*
    JSON
     */
    protected static final String ALUNO1_JSON = "aluno1.json";
    protected static final String ALUNO2_JSON = "aluno2.json";
    protected static final String ALUNO3_JSON = "aluno3.json";

    protected static final String DISCIPLINA1_JSON = "disciplina1.json";
    protected static final String DISCIPLINA2_JSON = "disciplina2.json";
    protected static final String DISCIPLINA3_JSON = "disciplina3.json";

    protected static final String PROFESSOR1_JSON = "professor1.json";
    protected static final String PROFESSOR2_JSON = "professor2.json";
    protected static final String PROFESSOR3_JSON = "professor3.json";

    protected static final String TURMA1_JSON = "turma1.json";
    protected static final String TURMA2_JSON = "turma2.json";
    protected static final String TURMA3_JSON = "turma3.json";

    /*
    GET OBJECTS
     */
    protected static Aluno getMockAluno1() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO1_JSON, Aluno.class);
    }
    protected static Aluno getMockAluno2() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO2_JSON, Aluno.class);
    }
    protected static Aluno getMockAluno3() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_ALUNO, ALUNO3_JSON, Aluno.class);
    }
    protected static Disciplina getMockDisciplina1() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA1_JSON, Disciplina.class);
    }
    protected static Disciplina getMockDisciplina2() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA2_JSON, Disciplina.class);
    }
    protected static Disciplina getMockDisciplina3() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_DISCIPLINA, DISCIPLINA3_JSON, Disciplina.class);
    }
    protected static Professor getMockProfessor1() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR1_JSON, Professor.class);
    }
    protected static Professor getMockProfessor2() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR2_JSON, Professor.class);
    }
    protected static Professor getMockProfessor3() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_PROFESSOR, PROFESSOR3_JSON, Professor.class);
    }
    protected static Turma getMockTurma1() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA1_JSON, Turma.class);
    }
    protected static Turma getMockTurma2() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA2_JSON, Turma.class);
    }
    protected static Turma getMockTurma3() {
        return LeitorJsonUtils.getMockObject(MOCK_FOLDER_TURMA, TURMA3_JSON, Turma.class);
    }

    public CargaResource(AlunoService alunoService,
                         DisciplinaService disciplinaService,
                         ProfessorService professorService, TurmaService turmaService) {
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.professorService = professorService;
        this.turmaService = turmaService;
    }

    @PostMapping("/carga-completa")
    public ResponseEntity<RestResponseDTO<String>> cargaCompleta(){
        alunoService.salvarAluno(getMockAluno1());
        alunoService.salvarAluno(getMockAluno2());
        alunoService.salvarAluno(getMockAluno3());

        disciplinaService.salvarDisciplina(getMockDisciplina1());
        disciplinaService.salvarDisciplina(getMockDisciplina2());
        disciplinaService.salvarDisciplina(getMockDisciplina3());

        professorService.salvarProfessor(getMockProfessor1());
        professorService.salvarProfessor(getMockProfessor2());
        professorService.salvarProfessor(getMockProfessor3());

        turmaService.cadastrarTurma(getMockTurma1());
        turmaService.cadastrarTurma(getMockTurma2());
        turmaService.cadastrarTurma(getMockTurma3());

        return (retornarSucesso("Carga completa realizada com sucesso"));

    }






}
