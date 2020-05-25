package io.github.lucasifce.gamification.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@GetMapping
    //@ResponseStatus(HttpStatus.OK) - ja retorna por padrao o status OK se der certo
    public List<Aluno> find(Aluno filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().
                withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Aluno> alunos = alunosRepository.findAll(example);
        return alunos;
    }
	
	@GetMapping("/{id}")
	public Aluno getAlunoById(@PathVariable("id") Long id){
		return alunosRepository.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
						"Cliente não encontrado!") );
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional
	public Aluno save(@RequestBody @Valid Aluno aluno) {
		Usuario usuario = usuariosRepository.save(aluno.getUsuario());
		aluno.setUsuario(usuario);
		return alunosRepository.save(aluno);
		//return aluno.getUsuario().toString();
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		alunosRepository.findById(id)
		.map(alunoExistente -> {
			alunosRepository.delete(alunoExistente);
			return alunoExistente;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Cliente não encontrado!") );
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody @Valid Aluno aluno, @PathVariable("id") Long id) {
		alunosRepository.findById(id)
		.map(alunoExistente -> {
			aluno.setId(alunoExistente.getId());
			alunosRepository.save(aluno);
			return aluno;
		}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Cliente não encontrado!") );
	}
	
	/*@GetMapping("/buscar-matricula/{matricula}")
	public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable("matricula") Long matricula){
		
		Optional<Aluno> aluno = alunosRepository.findByMatricula(matricula);
		
		if(aluno.isPresent()) {
			return ResponseEntity.ok(aluno.get());
		}
		
		return ResponseEntity.notFound().build();
	}*/
}
