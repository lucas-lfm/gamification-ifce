package io.github.lucasifce.gamification.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull
    @JsonProperty("criador_id")
    private Long criadorId;

}
