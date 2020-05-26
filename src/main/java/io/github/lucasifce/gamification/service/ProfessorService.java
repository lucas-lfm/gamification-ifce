package io.github.lucasifce.gamification.service;

import io.github.lucasifce.gamification.domain.model.Professor;

public interface ProfessorService {

    Professor save(Professor professor, Long id, String operacao);
    void deleteProfessor(Long id);

}
