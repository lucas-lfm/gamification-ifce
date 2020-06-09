package io.github.lucasifce.gamification.domain.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lucasifce.gamification.api.dto.ranking.RankingAlunoDTO;
import io.github.lucasifce.gamification.api.dto.ranking.RankingListAlunoDTO;
import io.github.lucasifce.gamification.api.dto.ranking.RankingListTurmaDTO;
import io.github.lucasifce.gamification.api.dto.ranking.RankingTurmaDTO;
import io.github.lucasifce.gamification.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasifce.gamification.domain.model.Ranking;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.RankingsRepository;
import io.github.lucasifce.gamification.domain.repository.TurmasRepository;
import io.github.lucasifce.gamification.domain.service.RankingService;

@Service
public class RankingServiceImplementation implements RankingService{

	@Autowired
	private RankingsRepository rankingsRepository;
	
	@Autowired
	private AlunosRepository alunosRepository;
	
	@Autowired
	private TurmasRepository turmasRepository;
	
	@Override
	public RankingListTurmaDTO buscarRankingPorTurma(Long idTurma){
		turmasRepository.findById(idTurma)
			.orElseThrow(() -> 
				new EntidadeNaoEncontradaException("Não foi possível retornar o Ranking. "
						+ "Turma solicitada não existe!"));
		
		List<Ranking> rankings = rankingsRepository.buscarPorTurma(idTurma);
		
		RankingListTurmaDTO rankingListTurma = new RankingListTurmaDTO();
		rankingListTurma.setIdTurma(idTurma);
		
		List<RankingTurmaDTO> rankingsDTO = new ArrayList<>();
		
		rankings.forEach(ranking -> {
			rankingsDTO.add( RankingTurmaDTO.builder()
					.matricula(ranking.getAluno().getMatricula())
					.nome(ranking.getAluno().getNome())
					.posicao(ranking.getPosicao())
					.pontuacao(ranking.getPontuacao())
					.build());
		});
		
		rankingListTurma.setRanking(rankingsDTO);
		
		return rankingListTurma;
		
	}
	
	@Override
	public RankingListAlunoDTO buscarRankingPorAluno(Long idAluno){
		alunosRepository.findById(idAluno)
			.orElseThrow(() -> 
					new EntidadeNaoEncontradaException("Não foi possível retornar o Ranking. "
							+ "Aluno solicitado não existe!"));
		
		List<Ranking> rankings = rankingsRepository.buscarPorAluno(idAluno);
		
		RankingListAlunoDTO rankingListAluno = new RankingListAlunoDTO();
		rankingListAluno.setIdAluno(idAluno);
		
		List<RankingAlunoDTO> rankingsDTO = new ArrayList<>();
		
		rankings.forEach( ranking -> {
			rankingsDTO.add( RankingAlunoDTO.builder()
					.codigoTurma(ranking.getTurma().getCodigo())
					.posicao(ranking.getPosicao())
					.pontuacao(ranking.getPontuacao())
					.build());
		});
		
		rankingListAluno.setRanking(rankingsDTO);
		return rankingListAluno;
	}
	
}
