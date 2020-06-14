package io.github.lucasifce.gamification.api.dto.turma;

import io.github.lucasifce.gamification.api.dto.aluno.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorDTO;
import io.github.lucasifce.gamification.domain.enums.StatusTurma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurmaFindDTO {

    private Long id;
    private String codigo;
    private String periodo;

    @Enumerated(EnumType.STRING)
    private StatusTurma status;

    private ProfessorDTO responsavel;
    private List<AlunoDTO> alunos;
    private List<ProfessorDTO> professores;


}
