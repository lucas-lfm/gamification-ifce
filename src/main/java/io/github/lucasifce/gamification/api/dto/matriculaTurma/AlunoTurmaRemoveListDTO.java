package io.github.lucasifce.gamification.api.dto.matriculaTurma;

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
public class AlunoTurmaRemoveListDTO {

    @NotNull
    @JsonProperty("id_professor")
    private Long idProfessor;

    @NotNull
    @JsonProperty("lista_alunos")
    private List<Long> listaAlunos;
}
