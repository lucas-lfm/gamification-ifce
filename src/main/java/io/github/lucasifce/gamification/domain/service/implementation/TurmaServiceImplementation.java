package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.TurmaDTO;
import io.github.lucasifce.gamification.domain.exception.NegocioListException;
import io.github.lucasifce.gamification.domain.model.Aluno;
import io.github.lucasifce.gamification.domain.model.MatriculaTurma;
import io.github.lucasifce.gamification.domain.model.Professor;
import io.github.lucasifce.gamification.domain.model.Turma;
import io.github.lucasifce.gamification.domain.repository.AlunosRepository;
import io.github.lucasifce.gamification.domain.repository.MatriculasTurmaRepository;
import io.github.lucasifce.gamification.domain.repository.ProfessoresRepository;
import io.github.lucasifce.gamification.domain.repository.TurmasRepository;
import io.github.lucasifce.gamification.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TurmaServiceImplementation implements TurmaService {

    @Autowired
    private TurmasRepository turmasRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private AlunosRepository alunosRepository;

    @Autowired
    private MatriculasTurmaRepository matriculasTurmaRepository;

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
        List<String> erros = validarCamposParaProfessor(dto.getIdTurma(), dto.getListaProfessores(), true);

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

    @Override
    @Transactional
    public void addNewListAluno(AlunoTurmaInsertListDTO dto){
        List<String> erros = validarCamposParaAluno(dto.getIdTurma(), dto.getIdProfessor(), dto.getListaAlunos(), true);

        if(erros.isEmpty()){
            Turma turma = pesquisarTurmaId(dto.getIdTurma()).get();

            List<MatriculaTurma> matriculaAlunos = new ArrayList<MatriculaTurma>();

            dto.getListaAlunos().stream()
                    .forEach(idAluno -> {
                        matriculaAlunos.add(
                            MatriculaTurma.builder()
                                .pontuacao(BigDecimal.valueOf(0L))
                                .aluno(pesquisarAlunoId(idAluno).get())
                                .turma(turma)
                                .build()
                        );
                    });
            matriculasTurmaRepository.saveAll(matriculaAlunos);
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    @Override
    @Transactional
    public void removeListAluno(AlunoTurmaInsertListDTO dto){
        List<String> erros = validarCamposParaAluno(dto.getIdTurma(), dto.getIdProfessor(), dto.getListaAlunos(), false);

        if(erros.isEmpty()){
            List<MatriculaTurma> matriculaAlunos = new ArrayList<MatriculaTurma>();

            dto.getListaAlunos().stream()
                    .forEach(idAluno -> {
                        matriculaAlunos.add(
                                matriculasTurmaRepository.buscarPorTurmaEAluno(idAluno, dto.getIdTurma()).get()
                        );
                    });
            matriculasTurmaRepository.deleteAll(matriculaAlunos);
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    @Override
    @Transactional
    public void removeListProfessor(ProfessorTurmaInsertListDTO dto){

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
    private List<String> validarCamposParaProfessor(Long idTurma, List<Long> idProfessores, boolean isNewProfessor){
        List<String> erros = new ArrayList<String>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        }
        idProfessores.stream()
                .forEach(idProfessor -> {
                    Optional<Professor> professorCadastro = pesquisarProfessorId(idProfessor);

                    if(professorCadastro.isEmpty()){
                        erros.add("Nenhum professor encontrado com o id: "+idProfessor);
                    } else {
                        if(isNewProfessor) {
                            int possuiProfessor = turmasRepository.verficarProfessorEmTurma(idProfessor, idTurma);
                            if (possuiProfessor > 0) {
                                erros.add("Esse professor de id: " + idProfessor + " já está inserido na turma.");
                            }
                        } /*else { //método para verificar se o professor é o criador
                            int professorCriadorTurma = turmasRepository.verificarCriadorTurma(idProfessor, idTurma);
                            if(professorCriadorTurma == 0){
                                erros.add("Você não pode remover o professor criador da turma. Delete a turma para isso");
                            }
                        }*/
                    }
                });
        return erros;
    }

    /*metodo para validar turma e aluno(s)*/
    private List<String> validarCamposParaAluno(Long idTurma, Long idProfessor, List<Long> idAlunos, boolean isNewProfessor){
        List<String> erros = new ArrayList<String>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);
        Optional<Professor> professor = pesquisarProfessorId(idProfessor);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        }
        if(professor.isEmpty()){
            erros.add("Nenhuma professor encontrado com o id: "+idProfessor);
        } else {
            int professorCadastrado = turmasRepository.verficarProfessorEmTurma(idProfessor, idTurma);

            if(professorCadastrado == 0){
                if(isNewProfessor) {
                    erros.add("Esse professor não pode inserir alunos, ele não está cadastro na turma.");
                } else {
                    erros.add("Esse professor não pode remover alunos, ele não está cadastro na turma.");
                }
            }
            idAlunos.forEach(idAluno -> {
                Optional<Aluno> alunoCadastro = pesquisarAlunoId(idAluno);

                if (alunoCadastro.isEmpty()) {
                    erros.add("Nenhuma aluno encontrado com o id: " + idAluno);
                } else {
                    if(isNewProfessor) {
                        int alunoInserido = turmasRepository.verficarAlunoEmTurma(idAluno, idTurma);
                        if (alunoInserido == 1) {
                            erros.add("Esse aluno de id: " + idAluno + " já está inserido na turma.");
                        }
                    } else {
                        int alunoInserido = turmasRepository.verficarAlunoEmTurma(idAluno, idTurma);
                        if (alunoInserido == 0) {
                            erros.add("Esse aluno de id: " + idAluno + " não está inserido na turma.");
                        }
                    }
                }
            });
        }
        return erros;
    }

    /*Pesquisa aluno por id*/
    private Optional<Aluno> pesquisarAlunoId(Long id){
        return alunosRepository.findById(id);
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
