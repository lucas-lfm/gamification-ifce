package io.github.lucasifce.gamification.api.dto.matriculaTurma;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaTurmaDTO {

	@NotNull
	private Long alunoId;
	@NotNull
	private Long turmaId;
	
	@JsonProperty(access = Access.READ_ONLY)
	private BigDecimal pontuacao;
	
}