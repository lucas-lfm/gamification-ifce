package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.AlunoUsuarioDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private UsuariosRepository usuariosRepository;

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping
    public List<Aluno> findAluno(Aluno filtro){
        return alunoService.findAluno(filtro);
    }

	@GetMapping("/dto")
	public List<AlunoDTO> findAlunoDTO(Aluno filtro){
		return alunoService.findAlunoDTO(filtro);
	}
	
	@GetMapping("/{matricula}")
	public Aluno getAlunoByMatricula(@PathVariable("matricula") Long matricula){
		return alunoService.getAlunoByMatricula(matricula);
	}

	@GetMapping("/dto/{matricula}")
	public AlunoDTO getAlunoByMatriculaDTO(@PathVariable("matricula") Long matricula){
		return alunoService.getAlunoByMatriculaDTO(matricula);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public AlunoUsuarioDTO save(@RequestBody @Valid Aluno aluno){
		return alunoService.save(aluno);
	}
	/*
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional
	public Aluno save(@RequestBody @Valid Aluno aluno) {
		
		//Inserir essa regra de negócio em uma classe de serviço (TESTADO)
		//-------------------------------------------------------------------
		Aluno alunoExistente = alunosRepository.findByMatricula(aluno.getMatricula());
		
		if(alunoExistente != null && !alunoExistente.equals(aluno)) {
			throw new NegocioException("Aluno já cadastrado!");
		}
		
		alunoExistente = alunosRepository.findByEmail(aluno.getEmail());
		
		if(alunoExistente != null && !alunoExistente.equals(aluno)) {
			throw new NegocioException("Email já cadastrado!");
		}
		
		Usuario usuario = usuariosRepository.findByLogin(aluno.getUsuario().getLogin());
		
		if(usuario != null && !usuario.equals(aluno.getUsuario())) {
			throw new NegocioException("Esse nome de usuário não está disponível!");
		}
		//-----------------------------------------------------------------------
		
		usuario = usuariosRepository.save(aluno.getUsuario());
		aluno.setUsuario(usuario);
		return alunosRepository.save(aluno);
		//return aluno.getUsuario().toString();
	}
	*/
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
