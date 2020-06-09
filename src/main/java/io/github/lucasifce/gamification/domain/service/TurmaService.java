package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.turma.TurmaDTO;

public interface TurmaService {

    TurmaDTO saveNewTurma(TurmaDTO turma);
    void addNewListProfessor(ProfessorTurmaInsertListDTO dto);
    void addNewListAluno(AlunoTurmaInsertListDTO dto);
    void removeListAluno(AlunoTurmaInsertListDTO dto);
    void removeListProfessor(ProfessorTurmaInsertListDTO dto);
}
