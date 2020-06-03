package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplementation implements UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private AlunosRepository alunosRepository;

    //ver possivel alteração de métodos, para deixar apenas os dtos em cada classe e deixar com todos os dados completos aqui
    @Override
    public List<Usuario> findUsuario(Usuario filtro){
        Example pesquisa = filtroPesquisa(filtro);
        return usuariosRepository.findAll(pesquisa);
    }

    @Override
    public Usuario update(Usuario usuario, Long id, Object tipoUsuario){
        Long idUsuario;
        var idTipoUsuario = buscaIdUsuarioComProfessorOuAluno(tipoUsuario, id);

        if(verificarIntanciaObjeto(tipoUsuario).equalsIgnoreCase("aluno")){
            idUsuario = ((Aluno) idTipoUsuario).getUsuario().getId();
        } else {
            idUsuario = ((Professor)idTipoUsuario).getUsuario().getId();
        }
        if(verificarCampos(usuario, idUsuario)){
            usuario.setId(idUsuario);
            usuario = usuariosRepository.save(usuario);
        }
        return usuario;
    }

    /*Metodo para validar campo de login de usuário*/
    private boolean verificarCampos(Usuario usuario, Long id){
        if (pesquisarCadastroUsuario(usuario.getLogin()) != null
                && !pesquisarCadastroUsuarioId(id).getLogin().equalsIgnoreCase(usuario.getLogin())) {
            throw new NegocioException("Login já pertece a outro usuário.");
        } else {
            return true;
        }
    }

    /*metodo para verificar instância do tipo do usuário e retornar o id do usuário de acordo com o id do aluno ou professor*/
    private Object buscaIdUsuarioComProfessorOuAluno(Object tipoUsuario, Long id){
        if (verificarIntanciaObjeto(tipoUsuario).equalsIgnoreCase("aluno")){
            Optional<Aluno> aluno = alunosRepository.findById(id);
            if(aluno.isEmpty()){
                throw new NegocioException("Usuário não encontrado");
            } else {
                return aluno.get();
            }
        } else {
            Optional<Professor> professor = professoresRepository.findById(id);
            if(professor.isEmpty()){
                throw new NegocioException("Usuário não encontrado");
            } else {
                return professor.get();
            }
        }
    }

    private String verificarIntanciaObjeto(Object tipoUsuario){
        return tipoUsuario.equals(Aluno.class) ? "aluno" : "professor";
    }

    /*verificar se já existe login */
    private Usuario pesquisarCadastroUsuario(String login){
        Optional<Usuario> usuario = usuariosRepository.findByLogin(login);
        return usuario.isPresent() ? usuario.get() : null;
    }

    /*verificar se já usuario por id*/
    private Usuario pesquisarCadastroUsuarioId(Long id){
        Optional<Usuario> usuario = usuariosRepository.findById(id);
        return usuario.isPresent() ? usuario.get() : null;
    }

    /*filtro de pesquisa para usuário*/
    private Example filtroPesquisa(Usuario filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return Example.of(filtro, exampleMatcher);
    }
}
