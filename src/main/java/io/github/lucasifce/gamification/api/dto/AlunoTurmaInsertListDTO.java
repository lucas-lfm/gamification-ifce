package io.github.lucasifce.gamification.api.dto;

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
public class AlunoTurmaInsertListDTO {

    @NotNull
    @JsonProperty("id_turma")
    private Long idTurma;

    @NotNull
    @JsonProperty("id_professor")
    private Long idProfessor;

    @NotNull
    @JsonProperty("lista_alunos")
    private List<Long> listaAlunos;

}
