package io.github.lucasifce.gamification.api.dto.ranking;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingAlunoDTO {

	@JsonProperty("codigo_turma")
	private String codigoTurma;
	private Long posicao;
	private BigDecimal pontuacao;
	
}
