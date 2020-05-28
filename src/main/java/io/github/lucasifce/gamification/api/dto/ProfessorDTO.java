package io.github.lucasifce.gamification.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorDTO {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String email;

    private String telefone;

}
