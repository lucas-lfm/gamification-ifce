package io.github.lucasifce.gamification.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class TrocaStatusTurmaDTO {

    @NotNull
    @JsonProperty("id_responsavel")
    private Long idResponsavel;

    @NotBlank
    @JsonProperty("status")
    private String status;

}
