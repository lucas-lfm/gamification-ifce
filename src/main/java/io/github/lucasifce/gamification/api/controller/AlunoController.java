package io.github.lucasifce.gamification.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

	@Autowired
	private AlunosRepository alunosRepository;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Aluno> listarTodos(){
		return alunosRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Aluno> buscarPorId(@PathVariable("id") Long id){
		Optional<Aluno> aluno = alunosRepository.findById(id);
		
		if(aluno.isPresent()) {
			return ResponseEntity.ok(aluno.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/buscar-matricula/{matricula}")
	@ResponseBody
	public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable("matricula") Long matricula){
		
		Optional<Aluno> aluno = alunosRepository.findByMatricula(matricula);
		
		if(aluno.isPresent()) {
			return ResponseEntity.ok(aluno.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
