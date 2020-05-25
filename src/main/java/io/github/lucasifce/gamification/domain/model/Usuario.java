package io.github.lucasifce.gamification.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", length = 60, nullable = false, unique = true)
    private String login;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    @Column(name = "admin", nullable = false)
    private boolean admin;

}
