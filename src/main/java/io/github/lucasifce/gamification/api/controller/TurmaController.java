package io.github.lucasifce.gamification.api.controller;

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
    public TurmaDTO saveNewTurma(@RequestBody @Valid TurmaDTO turma){
        return turmaService.saveNewTurma(turma);
    }

    @PostMapping("/inserir-professores")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewListProfessor(@RequestBody @Valid ProfessorTurmaInsertListDTO dto){
        turmaService.addNewListProfessor(dto);
    }

    @PostMapping("/inserir-alunos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewListAluno(@RequestBody @Valid AlunoTurmaInsertListDTO dto){
        turmaService.addNewListAluno(dto);
    }

    @DeleteMapping("/remover-alunos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeListAluno(@RequestBody @Valid AlunoTurmaInsertListDTO dto){
        turmaService.removeListAluno(dto);
    }
    
    @GetMapping("/ranking/{idTurma}")
    public RankingListTurmaDTO buscarRanking(@PathVariable("idTurma") Long idTurma) {
    	return rankingService.buscarRankingPorTurma(idTurma);
    }

}
