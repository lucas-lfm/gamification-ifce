package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import io.github.lucasifce.gamification.domain.model.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    List<ProfessorDTO> findProfessorDTO(Professor filtro);
    List<Professor> findProfessor(Professor filtro);

    ProfessorDTO getProfessorByIdDTO(Long id);
    Professor getProfessorById(Long id);

    Professor save(Professor professor);
    ProfessorDTO update(ProfessorDTO dto, Long id);
    void deleteProfessor(Long id);

}
