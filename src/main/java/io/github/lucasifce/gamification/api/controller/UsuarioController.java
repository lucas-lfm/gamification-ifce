package io.github.lucasifce.gamification.api.controller;

import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/professor/usuario/{id}") /*refatorara para pegar o id do prefessor ou do aluno!!!*/
    public Usuario updateProfessor(@RequestBody @Valid Usuario usuario, @PathVariable("id") Long id){
        return usuarioService.update(usuario, id);
    }

    @PutMapping("/aluno/usuario/{id}")
    public Usuario updateAluno(@RequestBody @Valid Usuario usuario, @PathVariable("id") Long id){
        return usuarioService.update(usuario, id);
    }


}