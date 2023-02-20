package com.gestaoensino.gestao_ensino.api.resouces;

import com.gestaoensino.gestao_ensino.api.assembler.TurmaAssembler;
import com.gestaoensino.gestao_ensino.api.dtos.RestResponseDTO;
import com.gestaoensino.gestao_ensino.api.dtos.TurmaDTO;
import com.gestaoensino.gestao_ensino.api.resouces.modelo.GestaoEnsinoResource;
import com.gestaoensino.gestao_ensino.domain.model.redis.Turma;
import com.gestaoensino.gestao_ensino.services.AlunoService;
import com.gestaoensino.gestao_ensino.services.DisciplinaService;
import com.gestaoensino.gestao_ensino.services.TurmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sistema-gestao-ensino/turma")
public class TurmaEscritaResource extends GestaoEnsinoResource {

    private final TurmaService turmaService;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final TurmaAssembler turmaAssembler;

    public TurmaEscritaResource(TurmaService turmaService,
                                AlunoService alunoService,
                                DisciplinaService disciplinaService,
                                TurmaAssembler turmaAssembler) {
        this.turmaService = turmaService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.turmaAssembler = turmaAssembler;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<RestResponseDTO<TurmaDTO>> cadastrarTurma(@RequestBody TurmaDTO turmaDTO){
        Turma turma = turmaAssembler.desmontaDto(turmaDTO);
        return retornarSucesso(turmaAssembler.montaDto(turmaService.cadastrarTurma(turma)));
    }

    @PutMapping(value = "/editar/{idTurma}")
    public ResponseEntity<RestResponseDTO<TurmaDTO>> editarTurma(@RequestBody String nome,
                                                                 @PathVariable Integer idTurma){
        return retornarSucesso(turmaAssembler.montaDto(turmaService.editarDisciplina(nome, idTurma)));
    }

//    @PatchMapping(value = "/adicionar-aluno/{idAluno}/{idTurma}")
//    public ResponseEntity<RestResponseDTO<String>> adicionarAluno(@PathVariable Long idAluno,
//                                                                  @PathVariable Long idTurma){
//        turmaService.adicionarAluno(idAluno, idTurma);
//        String nomeTurma = turmaService.buscarTurma(idTurma).getNome();
//        String nomeAluno = alunoService.buscarAluno(idAluno).getNomeCompleto();
//        return retornarSucesso(nomeAluno + " cadastrado com sucesso na turma " + nomeTurma);
//    }

//    @PatchMapping(value = "/remover-aluno/{idAluno}/{idTurma}")
//    public ResponseEntity<RestResponseDTO<String>> removerAluno(@PathVariable Long idAluno,
//                                                                  @PathVariable Long idTurma){
//        turmaService.removerAluno(idAluno, idTurma);
//        String nomeTurma = turmaService.buscarTurma(idTurma).getNome();
//        String nomeAluno = alunoService.buscarAluno(idAluno).getNomeCompleto();
//        return retornarSucesso(nomeAluno + " removido com sucesso na turma " + nomeTurma);
//    }
//
//    @PatchMapping(value = "/adicionar-disciplina/{idDisciplina}/{idTurma}")
//    public ResponseEntity<RestResponseDTO<String>> adicionarDisciplina(@PathVariable Long idDisciplina,
//                                                                  @PathVariable Long idTurma){
//        turmaService.adicionarDisciplina(idDisciplina, idTurma);
//        String nomeTurma = turmaService.buscarTurma(idTurma).getNome();
//        String nomeDisciplina = disciplinaService.buscarDisciplina(idDisciplina).getNome();
//        return retornarSucesso(nomeDisciplina + " cadastrada com sucesso na turma " + nomeTurma);
//    }

//    @PatchMapping(value = "/remover-disciplina/{idDisciplina}/{idTurma}")
//    public ResponseEntity<RestResponseDTO<String>> removerDisciplina(@PathVariable Long idDisciplina,
//                                                                @PathVariable Long idTurma){
//        turmaService.removerDisciplina(idDisciplina, idTurma);
//        String nomeTurma = turmaService.buscarTurma(idTurma).getNome();
//        String nomeDisciplina = disciplinaService.buscarDisciplina(idDisciplina).getNome();
//        return retornarSucesso(nomeDisciplina + " removida com sucesso na turma " + nomeTurma);
//    }


}
