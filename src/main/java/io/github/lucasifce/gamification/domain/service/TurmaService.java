package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaResponsavelTurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaStatusTurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TurmaFindDTO;
import io.github.lucasifce.gamification.domain.model.Turma;

import java.util.List;

public interface TurmaService {

    List<TurmaFindDTO> findTurma (Turma filtro);
    TurmaFindDTO findTurmaByCodigo(String codigo);
    TurmaDTO saveNewTurma(TurmaDTO turma);
    void addNewListProfessor(ProfessorTurmaInsertListDTO dto);
    void addNewListAluno(AlunoTurmaInsertListDTO dto);
    void removeListAluno(AlunoTurmaRemoveListDTO dto, Long idTurma);
    void removeListProfessor(ProfessorTurmaRemoveListDTO dto, Long idTurma);
    void updateProfessorResponsavel(TrocaResponsavelTurmaDTO dto, Long idTurma);
    TurmaDTO updateStatus(TrocaStatusTurmaDTO dto, Long idTurma);
}
