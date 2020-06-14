package io.github.lucasifce.gamification.domain.service;

import io.github.lucasifce.gamification.api.dto.aluno.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.aluno.AlunoTurmasDTO;
import io.github.lucasifce.gamification.api.dto.aluno.AlunoUsuarioDTO;
import io.github.lucasifce.gamification.api.dto.matriculaTurma.MatriculaTurmaDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;

import java.util.List;

public interface AlunoService {

    List<AlunoTurmasDTO> findAlunoTurmas(Aluno filtro);
    List<AlunoDTO> findAluno(Aluno filtro);

    AlunoTurmasDTO getAlunoTurmasByMatricula(Long matricula);
    AlunoDTO getAlunoByMatricula(Long matricula);
    
    AlunoUsuarioDTO getAlunoPorId(Long id);

    AlunoUsuarioDTO save(Aluno aluno);
    AlunoDTO update(AlunoDTO dto, Long id);
    void deleteAluno(Long id);//se se essa implementação é válida, pq pode deixar apenas ele inativo
    MatriculaTurmaDTO getPontuacaoPorTurma(Long turmaId, Long alunoId);

}
