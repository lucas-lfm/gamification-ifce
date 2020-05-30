package io.github.lucasifce.gamification.service.implementation;

import io.github.lucasifce.gamification.api.dto.ProfessorDTO;
import io.github.lucasifce.gamification.domain.exception.NegocioException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.service.ProfessorService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImplementation implements ProfessorService {

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public List<ProfessorDTO> findProfessorDTO(Professor filtro){
        Example example = filtroPesquisa(filtro);

        List<Professor> professores = professoresRepository.findAll(example);
        return professores.stream()
                .map(professor -> {
                    return converterProfessor(professor);
                }).collect(Collectors.toList());
    }

    @Override
    public List<Professor> findProfessor(Professor filtro){
        Example example = filtroPesquisa(filtro);

        //List<Professor> professores = professoresRepository.findAll(example);
        return professoresRepository.findAll(example);
    }

    @Override
    public Professor getProfessorById(Long id){
        return professoresRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Professor não encontrado."));
    }

    @Override
    public ProfessorDTO getProfessorByIdDTO(Long id){
        return converterProfessor(professoresRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Professor não encontrado.")));
    }

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

    /*Verifica se já existe um login desse usuário*/
    private Usuario pesquisarCadastroUsuario(String login){
        return usuariosRepository.findByLogin(login);
    }

    /*Verifica se já existe um email desse professor*/
    private Professor pesquisarCadastroEmailProfessor(String email){
        return professoresRepository.findByEmail(email);
    }

    /*Server para converter Professor para DTO*/
    private ProfessorDTO converterProfessor(Professor professor){
        return ProfessorDTO
                .builder()
                .id(professor.getId())
                .nome(professor.getNome())
                .email(professor.getEmail())
                .telefone(professor.getTelefone())
                .build();
    }

    /*Utiliza filtro para buscar professor pelos seus parâmetros, deixando de forma genéria para professor e professor dto*/
    private Example filtroPesquisa(Professor filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return Example.of(filtro, exampleMatcher);
    }

}
