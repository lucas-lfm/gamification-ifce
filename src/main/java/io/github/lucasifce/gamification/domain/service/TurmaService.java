package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.ProfessorTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.TurmaDTO;

public interface TurmaService {

    TurmaDTO saveNewTurma(TurmaDTO turma);
    void addNewListProfessor(ProfessorTurmaInsertListDTO dto);
    void addNewListAluno(AlunoTurmaInsertListDTO dto);
    void removeListAluno(AlunoTurmaInsertListDTO dto);
    void removeListProfessor(ProfessorTurmaRemoveListDTO dto);
}
