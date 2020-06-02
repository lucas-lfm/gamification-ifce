package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.AlunoUsuarioDTO;
import io.github.lucasifce.gamification.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public AlunoDTO getAlunoByMatriculaDTO(Long matricula) {
        return converterAluno(alunosRepository.findByMatricula(matricula)
               .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado.")));/*ver com o lucas se não é melhor colocar o no content*/

    }

    @Override
    public Aluno getAlunoByMatricula(Long matricula) {
        return alunosRepository.findByMatricula(matricula)
               .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado."));
    }

    @Override
    @Transactional
    public AlunoUsuarioDTO save(Aluno aluno){
        List<Exception> erros = new ArrayList<Exception>();
        if(pesquisarCadastroEmailAluno(aluno.getEmail()) == null){
            if(pesquisarCadastroUsuario(aluno.getUsuario().getLogin()) == null){
                if(pesquisarCadastroMatriculaAluno(aluno.getMatricula()) == null){
                    Usuario usuario = usuariosRepository.save(aluno.getUsuario());
                    aluno.setUsuario(usuario);
                    return converterAlunoUsuario(alunosRepository.save(aluno));
                } else {
                    erros.add(new NegocioException("Matrícula já cadastrada."));
                }
            } else {
                erros.add(new NegocioException("Nome de usuário já está em uso."));
            }
        } else {
            erros.add(new NegocioException("Email já cadastrado."));
        }
        if(!erros.isEmpty()){
            erros.forEach(err -> { throw new NegocioException(err.getMessage()); });
        }
        return null;
    }

    @Override
    public AlunoDTO update(AlunoDTO dto, Long id){
        Optional<Aluno> aluno = alunosRepository.findById(id);

        if(!aluno.isEmpty()){ //se existir algum aluno com esse id
            if(!aluno.get().getEmail().equalsIgnoreCase(dto.getEmail())
                && pesquisarCadastroEmailAluno(dto.getEmail()) != null){
                throw new NegocioException("Email já cadastrado.");
            } else {
                if(!aluno.get().getMatricula().equals(dto.getMatricula())
                        && pesquisarCadastroMatriculaAluno(dto.getMatricula()) != null){
                    throw new NegocioException("Matrícula já pertence a outro aluno.");
                } else {
                    aluno.get().setMatricula(dto.getMatricula());
                    aluno.get().setNome(dto.getNome());
                    aluno.get().setEmail(dto.getEmail());
                    aluno.get().setTelefone(dto.getTelefone());
                    return converterAluno(alunosRepository.save(aluno.get()));
                }
            }
        } else {
            throw new EntidadeNaoEncontradaException("Aluno não encontrado.");
        }
    }

    @Override
    @Transactional
    public void deleteAluno(Long id){
        alunosRepository.findById(id)
                .map(alunoExistente -> {
                    alunosRepository.delete(alunoExistente);
                    usuariosRepository.deleteById(alunoExistente.getUsuario().getId());
                    return alunoExistente;
                }).orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado."));
    }

    /*Verifica se já existe um login desse usuário*/
    private Usuario pesquisarCadastroUsuario(String login){
        Optional<Usuario> usuario = usuariosRepository.findByLogin(login);
        return usuario.isPresent() ? usuario.get() : null;
    }

    /*Verifica se já existe uma matricula desse aluno*/
    private Aluno pesquisarCadastroMatriculaAluno(Long matricula){
        Optional<Aluno> aluno = alunosRepository.findByMatricula(matricula);
        return aluno.isPresent() ? aluno.get() : null;
    }

    /*Verifica se já existe um email desse aluno*/
    private Aluno pesquisarCadastroEmailAluno(String email){
        Optional<Aluno> aluno = alunosRepository.findByEmail(email);
        return aluno.isPresent() ? aluno.get() : null; //se existir email já cadastrado retorna a instancia caso não null. Fazer isso para professor
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

    /*Metodo para converter Aluno para AlunoUsuarioDTO*/
    private AlunoUsuarioDTO converterAlunoUsuario(Aluno aluno){
        return AlunoUsuarioDTO
                .builder()
                .id(aluno.getId())
                .matricula(aluno.getMatricula())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .telefone(aluno.getTelefone())
                .usuario(aluno.getUsuario())
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
