package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.domain.exception.NegocioListException;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplementation implements UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    //ver possivel alteração de métodos, para deixar apenas os dtos em cada classe e deixar com todos os dados completos aqui
    @Override
    public List<Usuario> findUsuario(Usuario filtro){
        Example pesquisa = filtroPesquisa(filtro);
        return usuariosRepository.findAll(pesquisa);
    }

    @Override
    public Usuario update(Usuario usuario, Long id){
        List<String> erros = verificarCampos(usuario, id);

        if(erros.isEmpty()){
            usuario.setId(id);
            return usuariosRepository.save(usuario);
        } else {
            throw new NegocioListException(erros, "Validação campos.");
        }
    }

    /*Metodo para validar campo de login de usuário*/
    private List<String> verificarCampos(Usuario usuario, Long id){
        List<String> erros = new ArrayList<String>();
        Optional<Usuario> existeUsuario = usuariosRepository.findById(id);

        if(existeUsuario.isEmpty()){
            erros.add("Usuário não encontrado.");
        } else {
            if (pesquisarCadastroUsuario(usuario.getLogin()) != null
                    && !pesquisarCadastroUsuarioId(id).getLogin().equalsIgnoreCase(usuario.getLogin())) {
                erros.add("Login já pertece a outro usuário.");
            }
        }
        return erros;
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
