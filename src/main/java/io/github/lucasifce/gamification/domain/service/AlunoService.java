package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.AlunoUsuarioDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;

import java.util.List;

public interface AlunoService {

    List<AlunoDTO> findAlunoDTO(Aluno filtro);
    List<Aluno> findAluno(Aluno filtro);

    AlunoDTO getAlunoByMatriculaDTO(Long matricula);
    Aluno getAlunoByMatricula(Long matricula);

    AlunoUsuarioDTO save(Aluno aluno);
    AlunoDTO update(AlunoDTO dto, Long id);
    void deleteAluno(Long id);//se se essa implementação é válida, pq pode deixar apenas ele inativo

}
