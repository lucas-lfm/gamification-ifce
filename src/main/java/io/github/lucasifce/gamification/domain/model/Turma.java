package io.github.lucasifce.gamification.domain.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "turma")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	@NotNull
	@ManyToOne
	@JoinColumn(name = "criador_id")
	private Professor criadorTurma;

	@JsonIgnoreProperties("turmas")
	@ManyToMany
	@JoinTable(name = "matricula_turma",
			joinColumns = @JoinColumn(name = "turma_id"),
			inverseJoinColumns = @JoinColumn(name = "aluno_id"))
	//@ManyToMany(mappedBy = "turmas")
	private List<Aluno> alunos;

    @JsonIgnoreProperties("turmas")
	@ManyToMany
    @JoinTable(name = "professor_turma",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id"))
	private List<Professor> professores;
}
