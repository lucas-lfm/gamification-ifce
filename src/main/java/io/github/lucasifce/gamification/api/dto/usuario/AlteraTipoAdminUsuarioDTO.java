package io.github.lucasifce.gamification.api.dto.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class AlteraTipoAdminUsuarioDTO {

    @Column(name = "admin", nullable = false)
    private Boolean admin;

}
