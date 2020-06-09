package io.github.lucasifce.gamification.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.lucasifce.gamification.domain.model.Aluno;

public interface AlunosRepository extends JpaRepository<Aluno, Long>{
	
	Optional<Aluno> findByMatricula(Long matricula);
	Optional<Aluno> findByEmail(String email);

}
