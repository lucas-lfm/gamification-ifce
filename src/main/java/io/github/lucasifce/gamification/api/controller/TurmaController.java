package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaResponsavelTurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaStatusTurmaDTO;
import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.ranking.RankingListTurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TurmaDTO;
import io.github.lucasifce.gamification.domain.service.RankingService;
import io.github.lucasifce.gamification.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;
    
    @Autowired
    private RankingService rankingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaDTO saveNewTurma(@RequestBody @Valid TurmaDTO turma){
        return turmaService.saveNewTurma(turma);
    }

    @PostMapping("/inserir-professores")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewListProfessor(@RequestBody @Valid ProfessorTurmaInsertListDTO dto){
        turmaService.addNewListProfessor(dto);
    }

    @DeleteMapping("/remover-professores/{idTurma}")//corrigir para delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeListProfessor(@RequestBody @Valid ProfessorTurmaRemoveListDTO dto,
                                    @PathVariable("idTurma") Long idTurma){
        turmaService.removeListProfessor(dto, idTurma);
    }

    @PostMapping("/inserir-alunos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewListAluno(@RequestBody @Valid AlunoTurmaInsertListDTO dto){
        turmaService.addNewListAluno(dto);
    }

    @DeleteMapping("/remover-alunos/{idTurma}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeListAluno(@RequestBody @Valid AlunoTurmaRemoveListDTO dto, @PathVariable("idTurma") Long idTurma){
        turmaService.removeListAluno(dto, idTurma);
    }
    
    @GetMapping("/ranking/{idTurma}")
    public RankingListTurmaDTO buscarRanking(@PathVariable("idTurma") Long idTurma) {
    	return rankingService.buscarRankingPorTurma(idTurma);
    }

    @PatchMapping("/trocar-responsavel/{idTurma}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResponsavelTurma(@RequestBody @Valid TrocaResponsavelTurmaDTO dto,
                                       @PathVariable("idTurma") Long idTurma) {
        turmaService.updateProfessorResponsavel(dto, idTurma);
    }

    @PatchMapping("/trocar-status/{id_turma}")
    public TurmaDTO updateStatus(@RequestBody @Valid TrocaStatusTurmaDTO dto, @PathVariable("id_turma") Long idTurma) {
        return turmaService.updateStatus(dto, idTurma);
    }

}
