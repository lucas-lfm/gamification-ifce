package io.github.lucasifce.gamification.domain.repository;

import java.util.Optional;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.lucasifce.gamification.domain.model.MatriculaTurma;

public interface MatriculasTurmaRepository extends JpaRepository<MatriculaTurma, Long>{
	//@Query("SELECT p FROM MatriculaTurma p, Aluno a, Turma t WHERE a.id = :alunoId AND t.id = :turmaId") 
	//Query(value = "SELECT * FROM matricula_turma m INNER JOIN aluno a ON m.aluno_id = a.id INNER JOIN turma t ON m.turma_id = t.id WHERE a.id = :alunoId AND t.id = :turmaId", nativeQuery = true) 
	//@Query("select m from MatriculaTurma m inner join m.aluno a on m.aluno.id = a.id inner join m.turma t on m.turma.id = t.id where m.aluno.id = :alunoId and m.turma.id = :turmaId")
	
	@Query("SELECT m FROM MatriculaTurma m INNER JOIN m.aluno a INNER JOIN m.turma t WHERE a.id = :alunoId AND t.id = :turmaId")
	Optional<MatriculaTurma> pegarPontuacao(@Param("alunoId") Long alunoId, @Param("turmaId") Long turmaId);

	@Query("SELECT m FROM MatriculaTurma m WHERE m.id = :id")
	Optional<MatriculaTurma> buscarPorId(@Param("id") Long id);
	
}
