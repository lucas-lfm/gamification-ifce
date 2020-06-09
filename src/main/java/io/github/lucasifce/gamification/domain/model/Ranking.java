package io.github.lucasifce.gamification.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ranking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {

	@JsonIgnore
	@Id
	private Long id;
	private Long posicao;
	private BigDecimal pontuacao;
	
	@JsonIgnoreProperties(value = {"turmas","matriculas","usuario"})
	@OneToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;
	
	@JsonIgnoreProperties(value = {"alunos","professores","responsavelId"})
	@OneToOne
	@JoinColumn(name = "id_turma")
	private Turma turma;
	
}
