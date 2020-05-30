package io.github.lucasifce.gamification.api.modelDTO;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MatriculaTurmaInsertDTO {

	@NotNull
	private Long alunoId;
	
	@NotNull
	private Long turmaId;
	
}
