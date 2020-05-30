package io.github.lucasifce.gamification.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "turma")
public class Turma {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "codigo", length = 45, nullable = false, unique = true)
	private String codigo;
	
	@NotBlank
	@Column(name = "periodo", length = 45, nullable = false)
	private String periodo;
	
	@JsonIgnoreProperties("turmas")
	@ManyToMany
	@JoinTable(name = "matricula_turma",
			joinColumns = @JoinColumn(name = "turma_id"),
			inverseJoinColumns = @JoinColumn(name = "aluno_id"))
	//@ManyToMany(mappedBy = "turmas")
	private List<Aluno> alunos;
	
	
}
