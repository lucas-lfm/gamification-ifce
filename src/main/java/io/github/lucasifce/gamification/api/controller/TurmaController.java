package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.TurmaDTO;
import io.github.lucasifce.gamification.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public TurmaDTO saveNewTurma(@RequestBody @Valid TurmaDTO turma){
        return turmaService.saveNewTurma(turma);
    }

}
