package io.github.lucasifce.gamification.service.implementation;

import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.UsuariosRepository;
import io.github.lucasifce.gamification.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProfessorServiceImplementation implements ProfessorService {

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Transactional
    public void deleteProfessor(Long id){
        professoresRepository.findById(id)
                .map(professorExistente -> {
                    professoresRepository.delete(professorExistente);
                    usuariosRepository.deleteById(professorExistente.getUsuario().getId());
                    return professorExistente;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Professor não encontrado."));
    }


}
