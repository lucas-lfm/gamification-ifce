package io.github.lucasifce.gamification.api.dto.ranking;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingListAlunoDTO {

	@JsonProperty("id_aluno")
	private Long idAluno;
	
	private List<RankingAlunoDTO> ranking;
	
}
