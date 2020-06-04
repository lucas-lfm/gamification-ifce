package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.TurmaDTO;
import io.github.lucasifce.gamification.domain.exception.NegocioListException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Turma;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.TurmasRepository;
import io.github.lucasifce.gamification.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaServiceImplementation implements TurmaService {

    @Autowired
    private TurmasRepository turmasRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Override
    public TurmaDTO saveNewTurma(TurmaDTO turma){
        List<String> erros = validarCampos(turma);
        if(erros.isEmpty()){
            return converterTurma(turmasRepository.save(converterTurmaDTO(turma)));
        } else {
            throw new NegocioListException(erros, "Validar Campos");
        }
    }

    /*Validar campos da turma*/
    private List<String> validarCampos(TurmaDTO dto){
        List<String> erros = new ArrayList<>();
        Optional<Turma> turma = turmasRepository.findByCodigo(dto.getCodigo());
        Optional<Professor> professorCadastro = professoresRepository.findById(dto.getCriadorId());

        if(!turma.isEmpty()){
            erros.add("Código de turma já cadastrado.");
        }
        if(professorCadastro.isEmpty()){
            erros.add("Professor não com esse id está cadastrado.");
        }
        return erros;
    }

    /*mapeando turma para turmaDTO*/
    private TurmaDTO converterTurma(Turma turma){
        return TurmaDTO.builder()
                .id(turma.getId())
                .codigo(turma.getCodigo())
                .periodo(turma.getPeriodo())
                .criadorId(turma.getCriadorTurma().getId())
                .build();
    }

    /*mapeando turmaDTO para turma*/
    private Turma converterTurmaDTO(TurmaDTO dto){
        Optional<Professor> professor = professoresRepository.findById(dto.getCriadorId());
        return Turma.builder()
                .codigo(dto.getCodigo())
                .periodo(dto.getPeriodo())
                .criadorTurma(professor.get())
                .build();
    }

}
