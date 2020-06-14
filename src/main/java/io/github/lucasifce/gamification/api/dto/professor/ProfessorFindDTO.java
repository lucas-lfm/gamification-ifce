package io.github.lucasifce.gamification.api.dto.professor;

import io.github.lucasifce.gamification.api.dto.turma.TurmaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorFindDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private List<TurmaDTO> turmas;

}
