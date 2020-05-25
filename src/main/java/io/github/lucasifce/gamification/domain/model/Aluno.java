package io.github.lucasifce.gamification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

}
