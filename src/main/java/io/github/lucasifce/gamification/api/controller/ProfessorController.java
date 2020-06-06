package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/dto")
    public List<ProfessorDTO> findProfessorDTO(Professor filtro){
        return professorService.findProfessorDTO(filtro);
    }

    @GetMapping
    public List<Professor> findProfessor(Professor filtro){
        return professorService.findProfessor(filtro);
    }

    @GetMapping("/{id}")
    public Professor getProfessorById(@PathVariable("id") Long id){
        return professorService.getProfessorById(id);
    }

    @GetMapping("/dto/{id}")
    public ProfessorDTO getProfessorByIdDTO(@PathVariable("id") Long id){
        return professorService.getProfessorByIdDTO(id);
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
