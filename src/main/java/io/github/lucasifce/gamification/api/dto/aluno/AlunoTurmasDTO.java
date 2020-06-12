package io.github.lucasifce.gamification.api.dto.aluno;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.lucasifce.gamification.api.dto.matriculaTurma.MatriculaTurmaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoTurmasDTO {

	private Long id;

    @NotNull
    private Long matricula;

    @NotNull
    private String nome;

    @NotNull
    private String email;
    private String telefone;

    @JsonIgnoreProperties("id_aluno")
	private List<MatriculaTurmaDTO> matriculas;
    
}
