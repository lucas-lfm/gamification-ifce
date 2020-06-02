package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.AlunoUsuarioDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public AlunoUsuarioDTO save(@RequestBody @Valid Aluno aluno){
		return alunoService.save(aluno);
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
	
	@PutMapping("/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public AlunoDTO update(@RequestBody @Valid AlunoDTO aluno, @PathVariable("id") Long id) {
		return alunoService.update(aluno, id);
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
