package io.github.lucasifce.gamification.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.lucasifce.gamification.domain.model.Ranking;

public interface RankingsRepository extends JpaRepository<Ranking, Long> {

	@Query(value = "SELECT * FROM ranking WHERE id_turma = :idTurma", nativeQuery = true)
	List<Ranking> buscarPorTurma(@Param("idTurma") Long idTurma);
	
	@Query(value = "SELECT * FROM ranking WHERE id_aluno = :idAluno", nativeQuery = true)
	List<Ranking> buscarPorAluno(@Param("idAluno") Long idAluno);
	
	@Query(value = "SELECT * FROM ranking WHERE id_turma = :idTurma AND id_aluno = :idAluno", nativeQuery = true)
	List<Ranking> buscarPorTurmaEAluno(@Param("idTurma") Long idTurma, @Param("idAluno") Long idAluno);
	
}
