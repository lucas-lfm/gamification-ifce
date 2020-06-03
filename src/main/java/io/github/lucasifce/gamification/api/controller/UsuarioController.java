package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.api.dto.AlteraTipoAdminUsuario;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> findUsuario(Usuario filtro){
        return usuarioService.findUsuario(filtro);
    }

    @PutMapping("/professor/usuario/{id_professor}") /*refatorara para pegar o id do prefessor ou do aluno!!!*/
    public Usuario updateProfessor(@RequestBody @Valid Usuario usuario, @PathVariable("id_professor") Long id){
        return usuarioService.update(usuario, id, Professor.class);
    }

    @PutMapping("/aluno/usuario/{id_aluno}")
    public Usuario updateAluno(@RequestBody @Valid Usuario usuario, @PathVariable("id_aluno") Long id){
        return usuarioService.update(usuario, id, Aluno.class);
    }

    @PatchMapping("/usuario/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateAdminUsuario(@RequestBody @Valid AlteraTipoAdminUsuario admin, @PathVariable("id") Long id){
        usuarioService.updateAdminUsuario(admin, id);
    }


}