package io.github.lucasifce.gamification.api.dto.matriculaTurma;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaTurmaDTO {

	@JsonProperty("id_aluno")
	private Long idAluno;
	
	@JsonProperty("codigo_turma")
	private String codigoTurma;
	private String periodo;
	private BigDecimal pontuacao;
	
}