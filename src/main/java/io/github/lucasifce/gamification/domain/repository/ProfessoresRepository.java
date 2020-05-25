package io.github.lucasifce.gamification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lucasifce.gamification.domain.model.Professor;

public interface ProfessoresRepository extends JpaRepository<Professor, Long>{

}
