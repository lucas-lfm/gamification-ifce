package io.github.lucasifce.gamification.service.implementation;

import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ProfessorServiceImplementation implements ProfessorService {

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Transactional
    public Professor save(Professor professor, Long id, String operacao){
        boolean tipoOperacao = tipoOperacaoSave(operacao);

        if(pesquisarCadastroEmailProfessor(professor, id, tipoOperacao)) {
            if(pesquisarCadastroUsuario(professor.getUsuario(), id, tipoOperacao)) {
                return salvarProfessorBD(professor);
            }
        }
        return null;
    }

    private Professor salvarProfessorBD(Professor professor){
        Usuario usuario = usuariosRepository.save(professor.getUsuario());
        professor.setUsuario(usuario);
        return professoresRepository.save(professor);
    }

    private boolean pesquisarCadastroUsuario(Usuario usuario, Long id, boolean tipoOperacao){
        Usuario usuarioPesquisa = usuariosRepository.findByLogin(usuario.getLogin());

        if(tipoOperacao) {
            if (usuarioPesquisa != null) {
                throw new NegocioException("Nome de usuário já está em uso.");
            }
        } else {
            Optional<Professor> professor = professoresRepository.findById(id);
            Optional<Usuario> usuarioProfessor = usuariosRepository.findById(professor.get().getUsuario().getId());

            if (usuarioPesquisa != null && !usuario.getLogin().equalsIgnoreCase(usuarioProfessor.get().getLogin())) {
                throw new NegocioException("Nome de usuário já está em uso.");
            }
        }
        return true;
    }

    private boolean pesquisarCadastroEmailProfessor(Professor professor, Long id, boolean tipoOperacao){
        Professor pesquisaProfessor = professoresRepository.findByEmail(professor.getEmail());

        if(tipoOperacao) {
            if(pesquisaProfessor != null){
                throw new NegocioException("Email já cadastrado.");
            }
        } else {
            Optional<Professor> professorValidacao = professoresRepository.findById(id);

            if(pesquisaProfessor != null && !professor.getEmail().equalsIgnoreCase(professorValidacao.get().getEmail())){
                throw new NegocioException("Email já cadastrado.");
            }
        }
        return true;
    }

    private boolean tipoOperacaoSave(String op){
        return op.equals("new") ? true : false;
    }

    @Override
    @Transactional
    public void deleteProfessor(Long id){
        professoresRepository.findById(id)
                .map(professorExistente -> {
                    professoresRepository.delete(professorExistente);
                    usuariosRepository.deleteById(professorExistente.getUsuario().getId());
                    return professorExistente;
                }).orElseThrow(() -> new NegocioException("Professor não encontrado."));
    }


}
