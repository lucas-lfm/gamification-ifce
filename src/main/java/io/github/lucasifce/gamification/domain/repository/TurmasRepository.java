package io.github.lucasifce.gamification.domain.repository;

import io.github.lucasifce.gamification.api.dto.TurmaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lucasifce.gamification.domain.model.Turma;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TurmasRepository extends JpaRepository<Turma, Long>{

    Optional<Turma> findByCodigo(String codigo);

    /*@Modifying
    @Query( value = " INSERT INTO Turma (codigo, periodo, criador_id) VALUES (:dto.codigo, :dto.periodo, :dto.criador_id)",
            nativeQuery = true)
    TurmaDTO saveNewTurma(@Param("dto") TurmaDTO dto);*/

    //@Modifying
    //@Query( value = " INSERT INTO Turma (codigo, periodo, criador_id) VALUES (:codigo, :periodo, :criador_id)",
      //      nativeQuery = true)
    //@Query(value = " INSERT INTO Turma (codigo, periodo, criador_id) values (?1, ?2, ?3)",
     //      nativeQuery = true)
    //void saveNewTurma(String codigo, String periodo, Long criadorId);


}
