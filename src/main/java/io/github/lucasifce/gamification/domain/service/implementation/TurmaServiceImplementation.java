package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.TurmaDTO;
import io.github.lucasifce.gamification.domain.exception.NegocioListException;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Turma;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.TurmasRepository;
import io.github.lucasifce.gamification.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TurmaServiceImplementation implements TurmaService {

    @Autowired
    private TurmasRepository turmasRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Override
    @Transactional
    public TurmaDTO saveNewTurma(TurmaDTO turma){
        List<String> erros = validarCampos(turma);
        if(erros.isEmpty()){
            return converterTurma(turmasRepository.save(converterTurmaDTO(turma)));
        } else {
            throw new NegocioListException(erros, "Validar Campos");
        }
    }

    @Override
    @Transactional
    public void addNewListProfessor(ProfessorTurmaInsertListDTO dto){
        List<String> erros = validarCamposParaNovoProfessor(dto.getIdTurma(), dto.getListaProfessores());

        if(erros.isEmpty()){
            Turma turma = pesquisarTurmaId(dto.getIdTurma()).get();
            List<Professor> professores = turma.getProfessores();

            dto.getListaProfessores().stream()
                    .forEach(idProfessor -> {
                        professores.add(pesquisarProfessorId(idProfessor).get());
                    });

            turma.builder()
                    .alunos(Collections.EMPTY_LIST)
                    .professores(professores)
                    .build();
            turmasRepository.save(turma);
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    /*Validar campos da turma para nova turma*/
    private List<String> validarCampos(TurmaDTO dto){
        List<String> erros = new ArrayList<>();
        Optional<Turma> turma = turmasRepository.findByCodigo(dto.getCodigo());
        Optional<Professor> professorCadastro = pesquisarProfessorId(dto.getCriadorId());

        if(!turma.isEmpty()){
            erros.add("Código de turma já cadastrado.");
        }
        if(professorCadastro.isEmpty()){
            erros.add("Professor não cadastrado.");
        }
        return erros;
    }

    /*metodo para validar turma e professor(es)*/
    private List<String> validarCamposParaNovoProfessor(Long idTurma, List<Long> idProfessores){
        List<String> erros = new ArrayList<String>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        }
        idProfessores.stream()
                .forEach(idProfessor -> {
                    System.out.println("Id professor: "+idProfessor);
                    Optional<Professor> professorCadastro = pesquisarProfessorId(idProfessor);
                    if(professorCadastro.isEmpty()){
                        erros.add("Nenhuma professor encontrado com o id: "+idProfessor);
                    } else {
                        int possuiProfessor = turmasRepository.verficarProfessorEmTurma(idProfessor, idTurma);
                        if(possuiProfessor > 0){
                            erros.add("Esse professor de id: "+idProfessor+" já está inserido na turma.");
                        }
                    }
                });
        return erros;
    }

    /*Pesquisa professor por id*/
    private Optional<Professor> pesquisarProfessorId(Long id){
        return professoresRepository.findById(id);
    }

    /*Pesquisa turma por id*/
    private Optional<Turma> pesquisarTurmaId(Long id){
        return turmasRepository.findById(id);
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
                .professores(Arrays.asList(professor.get()))
                .build();
    }

}
