package io.github.lucasifce.gamification.api.dto.professor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorTurmaRemoveListDTO {

    @NotNull
    @JsonProperty("lista_professores")
    private List<Long> listaProfessores;

}
