package io.github.lucasifce.gamification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricula", nullable = false, unique = true)
    @NotNull
    private Long matricula;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank
    private String nome;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;
    
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Valid
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
   
   /*@ManyToMany(targetEntity = MatriculaTurma.class, fetch = FetchType.EAGER)
	@JoinTable(name = "matricula_turma",
			joinColumns = @JoinColumn(name = "aluno_id"),
			inverseJoinColumns = @JoinColumn(name = "turma_id"))*/
    @JsonIgnoreProperties("alunos")
    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;
    
    @JsonIgnoreProperties(value = {"aluno", "turma"})
	@OneToMany(mappedBy = "aluno")
	private List<MatriculaTurma> matriculas;

}
