package io.github.lucasifce.gamification.api.dto.turma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrocaResponsavelTurmaDTO {

    @NotNull
    @JsonProperty("id_responsavel")
    private Long idResponsavel;

    @NotNull
    @JsonProperty("id_novo_responsavel")
    private Long idNovoResponsavel;

}
