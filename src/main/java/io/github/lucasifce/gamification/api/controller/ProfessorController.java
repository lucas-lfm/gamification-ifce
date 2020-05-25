package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

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
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Professor não encontrado."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Transactional
    public Professor save(@RequestBody Professor professor){
        Usuario usuario = usuariosRepository.save(professor.getUsuario());
        professor.setUsuario(usuario);
        return professoresRepository.save(professor);
    }

    @PutMapping("/{id}")
    public Professor update(@RequestBody Professor professor, @PathVariable("id") Long id){
        return professoresRepository.findById(id)
                .map(professorExistente -> {
                    professor.setId(professorExistente.getId());
                    professoresRepository.save(professor);
                    return professor;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Professor não encontrado."));
    }

    @PutMapping("/usuario/{id}")
    public void updateUsuarioProfessor(@RequestBody Usuario usuario, @PathVariable("id") Long id){/*Esse método pode ficar na usuario repositóry, depois refatorar*/
        usuariosRepository.findById(id)
                .map(usuarioExistente -> {
                    usuario.setId(usuarioExistente.getId());
                    usuariosRepository.save(usuario);
                    return usuario;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado."));
    }

}
