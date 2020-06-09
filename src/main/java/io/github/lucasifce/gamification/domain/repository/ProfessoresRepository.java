package io.github.lucasifce.gamification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lucasifce.gamification.domain.model.Professor;

import java.util.Optional;

public interface ProfessoresRepository extends JpaRepository<Professor, Long>{

    Optional<Professor> findByEmail(String email);
    //Optional<Professor> findById(Long id);

    //@Query(" update Professor p set p.nome = :nome, p.email = :email, p.telefone = :telefone where p.id = :id ")
    //ProfessorDTO update(ProfessorDTO dto, @Param("id") Long id);
    //ProfessorDTO save(Professor professor);
}
