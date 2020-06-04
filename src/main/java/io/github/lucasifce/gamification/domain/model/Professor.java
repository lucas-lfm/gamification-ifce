package io.github.lucasifce.gamification.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
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

	@JsonIgnoreProperties(value = {"professores", "alunos"})
	@ManyToMany(mappedBy = "professores")
	private List<Turma> turmas;
}
