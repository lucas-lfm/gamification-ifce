package io.github.lucasifce.gamification.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AlteraTipoAdminUsuario {

    @Column(name = "admin", nullable = false)
    private Boolean admin;

}
