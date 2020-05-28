package io.github.lucasifce.gamification.service.implementation;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
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
    public Professor save(Professor professor){
        if(pesquisarCadastroEmailProfessor(professor.getEmail()) == null) {
            if(pesquisarCadastroUsuario(professor.getUsuario().getLogin()) == null) {
                Usuario usuario = usuariosRepository.save(professor.getUsuario());
                professor.setUsuario(usuario);
                return professoresRepository.save(professor);
            } else {
                throw new NegocioException("Nome de usuário já está em uso.");
            }
        } else {
            throw new NegocioException("Email já cadastrado.");
        }
    }

    /*Verifica se já existe um login desse usuário, caso sim lança um exceção se não retorna true*/
    private Usuario pesquisarCadastroUsuario(String login){
        return usuariosRepository.findByLogin(login);
    }

    /*Verifica se já existe um email desse professor, caso sim lança um exceção se não retorna true*/
    private Professor pesquisarCadastroEmailProfessor(String email){
        return professoresRepository.findByEmail(email);
    }

    @Override
    public ProfessorDTO update(ProfessorDTO dto, Long id) {
        Optional<Professor> professor = professoresRepository.findById(id);

        if(!professor.isEmpty()) {
            if (!professor.get().getEmail().equalsIgnoreCase(dto.getEmail())
                    && pesquisarCadastroEmailProfessor(dto.getEmail()) != null) {
                throw new NegocioException("Email já cadastrado.");
            } else {
                professor.get().setNome(dto.getNome());
                professor.get().setEmail(dto.getEmail());
                professor.get().setTelefone(dto.getTelefone());
                return converterProfessor(professoresRepository.save(professor.get()));
            }
        } else {
            throw new NegocioException("Professor não encontrado.");
        }
    }

    private ProfessorDTO converterProfessor(Professor professor){
        return ProfessorDTO
                .builder()
                .id(professor.getId())
                .nome(professor.getNome())
                .email(professor.getEmail())
                .telefone(professor.getTelefone())
                .build();
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
