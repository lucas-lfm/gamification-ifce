package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import java.util.List;


import javax.validation.Valid;



@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> find(Professor filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);
        List<Professor> professores = professoresRepository.findAll(example);
        return professores;
    }

    @GetMapping("/{id}")
    public Professor getProfessorById(@PathVariable("id") Long id){
        return professoresRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Professor não encontrado."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Professor save(@RequestBody @Valid Professor professor){
        return professorService.save(professor);
    }

    @PutMapping("/{id}")
    public ProfessorDTO update(@RequestBody @Valid ProfessorDTO professor, @PathVariable("id") Long id){
        return professorService.update(professor, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProfessor(@PathVariable("id") Long id){
        professorService.deleteProfessor(id);
    }

}
