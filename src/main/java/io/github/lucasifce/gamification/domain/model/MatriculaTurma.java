package io.github.lucasifce.gamification.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "matricula_turma")
public class MatriculaTurma {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pontuacao", scale = 2, precision = 5)
	private BigDecimal pontuacao;
	
	@JsonIgnoreProperties(value = {"turmas", "matriculas"})
	@ManyToOne
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;
	
	@JsonIgnoreProperties(value = {"alunos"})
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	
}
