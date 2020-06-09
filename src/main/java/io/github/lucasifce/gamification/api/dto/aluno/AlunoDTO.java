package io.github.lucasifce.gamification.api.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoDTO {

    private Long id;

    @NotNull
    private Long matricula;

    @NotNull
    private String nome;

    @NotNull
    private String email;
    private String telefone;

}
