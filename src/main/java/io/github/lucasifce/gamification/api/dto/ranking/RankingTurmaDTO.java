package io.github.lucasifce.gamification.api.dto.ranking;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingTurmaDTO {
	
	private Long matricula;
	private String nome;
	private Long posicao;
	private BigDecimal pontuacao;
	
}
