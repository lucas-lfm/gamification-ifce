package io.github.lucasifce.gamification.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lucasifce.gamification.domain.enums.StatusTurma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaDTO {

    private Long id;

    @NotBlank
    private String codigo;

    @NotBlank
    private String periodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTurma status;

    @NotNull
    @JsonProperty("id_responsavel")
    private Long responsavelId;



}
