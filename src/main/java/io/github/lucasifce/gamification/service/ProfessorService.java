package io.github.lucasifce.gamification.service;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import io.github.lucasifce.gamification.domain.model.Professor;

public interface ProfessorService {

    Professor save(Professor professor);
    ProfessorDTO update(ProfessorDTO dto, Long id);
    void deleteProfessor(Long id);

}
