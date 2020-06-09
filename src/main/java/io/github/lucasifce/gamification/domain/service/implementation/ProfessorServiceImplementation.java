package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.professor.ProfessorDTO;
import io.github.lucasifce.gamification.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasifce.gamification.domain.exception.NegocioListException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Usuario;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.domain.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado."));
    }

    @Override
    public ProfessorDTO getProfessorByIdDTO(Long id){
        return converterProfessor(professoresRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado.")));
    }

    @Override
    @Transactional
    public Professor save(Professor professor){
        List<String> erros = validarCampos(professor);

        if(erros.isEmpty()){
            Usuario usuario = usuariosRepository.save(professor.getUsuario());
            professor.setUsuario(usuario);
            professor = professoresRepository.save(professor);
            professor.setTurmas(Collections.EMPTY_LIST);
            return professor;
        } else {
            throw new NegocioListException(erros, "Validação campos.");
        }
    }

    @Override
    public ProfessorDTO update(ProfessorDTO dto, Long id) {
        Optional<Professor> professor = professoresRepository.findById(id);
        List<String> erros = validarCampos(professor, dto);

        if(erros.isEmpty()){
            professor.get().setNome(dto.getNome());
            professor.get().setEmail(dto.getEmail());
            professor.get().setTelefone(dto.getTelefone());
            return converterProfessor(professoresRepository.save(professor.get()));
        } else {
            throw new NegocioListException(erros, "Validação campos.");
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
                }).orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado."));
    }

    /*metodo para validar campos para salvar*/
    private List<String> validarCampos(Professor professor){
        List<String> erros = new ArrayList<String>();

        if(pesquisarCadastroEmailProfessor(professor.getEmail()) != null) {
            erros.add("Email já cadastrado.");
        }
        if(pesquisarCadastroUsuario(professor.getUsuario().getLogin()) != null) {
            erros.add("Nome de usuário já está em uso.");
        }
        return erros;
    }

    /*metodo para validar campos para atualizar*/
    private List<String> validarCampos(Optional<Professor> professor, ProfessorDTO dto){
        List<String> erros = new ArrayList<String>();

        if(professor.isEmpty()){
            erros.add("Professor não encontrado.");
        } else {
            if (!professor.get().getEmail().equalsIgnoreCase(dto.getEmail())
                    && pesquisarCadastroEmailProfessor(dto.getEmail()) != null) {
                erros.add("Email já cadastrado.");
            }
        }
        return erros;
    }

    /*Verifica se já existe um login desse usuário*/
    private Usuario pesquisarCadastroUsuario(String login){
        Optional<Usuario> usuario = usuariosRepository.findByLogin(login);
        return usuario.isPresent() ? usuario.get() : null;
    }

    /*Verifica se já existe um email desse professor*/
    private Professor pesquisarCadastroEmailProfessor(String email){
        Optional<Professor> professor = professoresRepository.findByEmail(email);
        return professor.isPresent() ? professor.get() : null;
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
