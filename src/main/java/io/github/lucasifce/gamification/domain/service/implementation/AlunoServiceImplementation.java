package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImplementation implements AlunoService {

    @Autowired
    private AlunosRepository alunosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;


    @Override
    public List<AlunoDTO> findAlunoDTO(Aluno filtro) {
        Example example = filtroPesquisa(filtro);
        List<Aluno> alunos = alunosRepository.findAll(example);
        return alunos.stream()
                .map(aluno -> {
                    return converterAluno(aluno);
                }).collect(Collectors.toList());
    }

    @Override
    public List<Aluno> findAluno(Aluno filtro) {
        Example example = filtroPesquisa(filtro);
        return alunosRepository.findAll(example);
    }

    /*Metodo para converter Aluno para AlunoDTO*/
    private AlunoDTO converterAluno(Aluno aluno){
        return AlunoDTO
                .builder()
                .id(aluno.getId())
                .matricula(aluno.getMatricula())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .telefone(aluno.getTelefone())
                .build();
    }

    /*método para filtro*/
    private Example filtroPesquisa(Aluno filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return Example.of(filtro, exampleMatcher);
    }
}
