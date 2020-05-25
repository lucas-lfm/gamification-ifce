package io.github.lucasifce.gamification.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", length = 60, nullable = false, unique = true)
    @NotBlank(message = "{campo.login.obrigatorio}")
    private String login;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "senha", length = 100, nullable = false)
    @NotBlank(message = "{campo.senha.obrigatorio}")
    private String senha;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    public Usuario(Long id, String login, String senha, boolean admin) {
        super();
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario(String login, String senha, boolean admin) {
        super();
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", admin=" + admin + "]";
    }
	
}
