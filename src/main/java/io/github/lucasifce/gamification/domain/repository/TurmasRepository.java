package io.github.lucasifce.gamification.domain.repository;

import io.github.lucasifce.gamification.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TurmasRepository extends JpaRepository<Turma, Long>{

    @Query(value = "SELECT count(*)  FROM professor_turma as pt INNER JOIN professor as p on p.id = pt.professor_id "+
            "INNER JOIN turma as t on t.id = pt.turma_id WHERE p.id = :idProfessor and t.id = :idTurma", nativeQuery = true)
    int verficarProfessorEmTurma(@Param("idProfessor") Long idProfessor, @Param("idTurma") Long idTurma);

    Optional<Turma> findByCodigo(String codigo);

    //@Modifying
    //@Query( value = " INSERT INTO Turma (codigo, periodo, criador_id) VALUES (:codigo, :periodo, :criador_id)",
      //      nativeQuery = true)
    //void saveNewTurma(String codigo, String periodo, Long criadorId);

    //@Query(value = "INSERT INTO professor_turma (turma_id, professor_id) VALUES (?1, ?2)", nativeQuery = true)
    //void addProfessor(Long idTurma, Long idProfessor);

}
