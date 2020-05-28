package io.github.lucasifce.gamification.domain.repository;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lucasifce.gamification.domain.model.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessoresRepository extends JpaRepository<Professor, Long>{

    Professor findByEmail(String email);

    //@Query(" update Professor p set p.nome = :nome, p.email = :email, p.telefone = :telefone where p.id = :id ")
    //ProfessorDTO update(ProfessorDTO dto, @Param("id") Long id);
    //ProfessorDTO save(Professor professor);
}
