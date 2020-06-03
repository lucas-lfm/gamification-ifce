package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.domain.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> findUsuario(Usuario filtro);
    Usuario update(Usuario usuario, Long id, Object tipoUsuario);
}
