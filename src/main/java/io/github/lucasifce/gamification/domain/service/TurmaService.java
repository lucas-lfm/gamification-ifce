package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.*;
import io.github.lucasifce.gamification.domain.enums.StatusTurma;

public interface TurmaService {

    TurmaDTO saveNewTurma(TurmaDTO turma);
    void addNewListProfessor(ProfessorTurmaInsertListDTO dto);
    void addNewListAluno(AlunoTurmaInsertListDTO dto);
    void removeListAluno(AlunoTurmaInsertListDTO dto);
    void removeListProfessor(ProfessorTurmaRemoveListDTO dto);
    void updateProfessorResponsavel(TrocaResponsavelTurmaDTO dto);
    TurmaDTO updateStatus(TrocaStatusTurmaDTO dto, Long idTurma);
}
