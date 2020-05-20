package io.github.lucasifce.gamification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lucasifce.gamification.domain.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{ 

}
