package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.TurmaDTO;

public interface TurmaService {

    TurmaDTO saveNewTurma(TurmaDTO turma);
    void addNewListProfessor(ProfessorTurmaInsertListDTO dto);

}
