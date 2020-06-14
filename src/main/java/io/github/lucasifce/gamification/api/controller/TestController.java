package io.github.lucasifce.gamification.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.lucasifce.gamification.api.dto.matriculaTurma.MatriculaTurmaDTO;
import io.github.lucasifce.gamification.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.MatriculaTurma;
import io.github.lucasifce.gamification.domain.model.Ranking;
import io.github.lucasifce.gamification.domain.model.Turma;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.MatriculasTurmaRepository;
import io.github.lucasifce.gamification.domain.repository.RankingsRepository;
import io.github.lucasifce.gamification.domain.repository.TurmasRepository;

@RestController
@RequestMapping("/api/testes")
public class TestController {

	@Autowired
	private TurmasRepository turmasRepository;
	
	@Autowired
	private MatriculasTurmaRepository matriculasTurmaRepository;
	
	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private RankingsRepository rankingsRepository;
	
	@GetMapping
    //@ResponseStatus(HttpStatus.OK) - ja retorna por padrao o status OK se der certo
    public List<Turma> find(Turma filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().
                withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Turma> turmas = turmasRepository.findAll(example);
        return turmas;
    }
	
	//alunos/pontuacao?turmaId=1&alunoId=2
	//alunos/pontuacao/2/1
	
	@GetMapping("/pontuacao/{id}")
    //@ResponseStatus(HttpStatus.OK) - ja retorna por padrao o status OK se der certo
    public MatriculaTurma getPontuacao(@PathVariable Long id){
        
		//Optional<MatriculaTurma> matricula = matriculasTurmaRepository.pegarPontuacao(alunoId, turmaId);
		
		Optional<MatriculaTurma> matricula = matriculasTurmaRepository.buscarPorId(id);
		
		return matricula.orElseThrow(() -> new NegocioException("Registro não encontrado!"));
		
    }
	
	@GetMapping("/pontuacao/{turmaId}/{alunoId}")
    //@ResponseStatus(HttpStatus.OK) - ja retorna por padrao o status OK se der certo
    public MatriculaTurma getPontuacao(@PathVariable Long turmaId, @PathVariable Long alunoId){
        
		//Optional<MatriculaTurma> matricula = matriculasTurmaRepository.pegarPontuacao(alunoId, turmaId);
		
		Optional<MatriculaTurma> matricula = matriculasTurmaRepository.buscarPorTurmaEAluno(alunoId, turmaId);
		
		return matricula.orElseThrow(() -> new NegocioException("Registro não encontrado!"));
		
    }
	
	@PostMapping("/turmas")
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional
	public Turma save(@RequestBody @Valid Turma turma) {
		return turmasRepository.save(turma);
	}
	
	@GetMapping("/ranking/{idTurma}")
	public List<Ranking> buscarRankingPorTurma(@PathVariable Long idTurma){
		return rankingsRepository.buscarPorTurma(idTurma);
	}
	
	@GetMapping("/ranking/{idTurma}/{idAluno}")
	public List<Ranking> buscarRankingPorTurmaEAluno(@PathVariable Long idTurma, @PathVariable Long idAluno){
		return rankingsRepository.buscarPorTurmaEAluno(idTurma, idAluno);
	}
	
}
