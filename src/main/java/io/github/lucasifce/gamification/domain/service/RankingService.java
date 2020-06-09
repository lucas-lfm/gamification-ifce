package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.ranking.RankingListAlunoDTO;
import io.github.lucasifce.gamification.api.dto.ranking.RankingListTurmaDTO;

public interface RankingService {

	public RankingListTurmaDTO buscarRankingPorTurma(Long idTurma);
	
	public RankingListAlunoDTO buscarRankingPorAluno(Long idAluno);
	
}
