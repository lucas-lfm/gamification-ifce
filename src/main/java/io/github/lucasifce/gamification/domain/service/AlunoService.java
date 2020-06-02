package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;

import java.util.List;

public interface AlunoService {

    List<AlunoDTO> findAlunoDTO(Aluno filtro);
    List<Aluno> findAluno(Aluno filtro);

    /*AlunoDTO getProfessorByIdDTO(Long id);
    Professor getProfessorById(Long id);

    Professor save(Professor professor);
    ProfessorDTO update(ProfessorDTO dto, Long id);
    void deleteProfessor(Long id);*/

}
