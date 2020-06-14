package io.github.lucasifce.gamification.domain.service.implementation;

import io.github.lucasifce.gamification.api.dto.aluno.AlunoDTO;
import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaRemoveListDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaResponsavelTurmaDTO;
import io.github.lucasifce.gamification.api.dto.turma.TrocaStatusTurmaDTO;
import io.github.lucasifce.gamification.api.dto.matriculaTurma.AlunoTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.professor.ProfessorTurmaInsertListDTO;
import io.github.lucasifce.gamification.api.dto.turma.TurmaDTO;

import io.github.lucasifce.gamification.api.dto.turma.TurmaFindDTO;
import io.github.lucasifce.gamification.domain.enums.StatusTurma;
import io.github.lucasifce.gamification.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasifce.gamification.domain.exception.ListaVaziaException;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<TurmaFindDTO> findTurma(Turma filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);

        List<Turma> turmas = turmasRepository.findAll(example);
        if(!turmas.isEmpty()) {
            return turmas.stream().map(turma -> converterTurmaFindDTO(turma))
                    .collect(Collectors.toList());
        } else {
            throw new ListaVaziaException();
        }
    }

    @Override
    public TurmaFindDTO findTurmaByCodigo(String codigo){
        Optional<Turma> turma = turmasRepository.findByCodigo(codigo);

        if(!turma.isEmpty()) {
            return converterTurmaFindDTO(turma.get());
        } else {
            throw new EntidadeNaoEncontradaException("Turma com esse código não encontrada.");
        }
    }

    @Override
    @Transactional
    public TurmaDTO saveNewTurma(TurmaDTO turma){
        List<String> erros = validarCampos(turma);
        if(erros.isEmpty()){
            turma.setStatus(StatusTurma.ATIVO);
            return converterTurma(turmasRepository.save(converterTurmaDTO(turma)));
        } else {
            throw new NegocioListException(erros, "Validar Campos");
        }
    }

    @Override
    @Transactional
    public void addNewListProfessor(ProfessorTurmaInsertListDTO dto){
        List<String> erros = validarCamposParaProfessorInsert(dto);

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
    public void removeListProfessor(ProfessorTurmaRemoveListDTO dto, Long idTurma){
        List<String> erros = validarCamposParaProfessorRemove(dto, idTurma);

        if(erros.isEmpty()){
            dto.getListaProfessores().stream()
                .forEach(idProfessor -> {
                    turmasRepository.deletarProfessorTurma(idProfessor, idTurma);
                });
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
    public void removeListAluno(AlunoTurmaRemoveListDTO dto, Long idTurma){
        List<String> erros = validarCamposParaAluno(idTurma, dto.getIdProfessor(), dto.getListaAlunos(), false);

        if(erros.isEmpty()){
            List<MatriculaTurma> matriculaAlunos = new ArrayList<MatriculaTurma>();

            dto.getListaAlunos().stream()
                    .forEach(idAluno -> {
                        matriculaAlunos.add(
                                matriculasTurmaRepository.buscarPorTurmaEAluno(idAluno, idTurma).get()
                        );
                    });
            matriculasTurmaRepository.deleteAll(matriculaAlunos);
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    @Override
    @Transactional
    public void updateProfessorResponsavel(TrocaResponsavelTurmaDTO dto, Long idTurma){
        List<String> erros = validarCamposTrocaResponsavel(dto, idTurma);

        if(erros.isEmpty()){
            turmasRepository.updateProfessorResponsavel(dto.getIdNovoResponsavel(), idTurma);
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    @Override
    @Transactional
    public TurmaDTO updateStatus(TrocaStatusTurmaDTO dto, Long idTurma){
        List<String> erros = validarCamposTrocaStatus(dto, idTurma);

        if(erros.isEmpty()){
            turmasRepository.updateStatusTurma(dto.getStatus().toUpperCase(), idTurma);
            TurmaDTO turma = converterTurma(pesquisarTurmaId(idTurma).get());
            turma.setStatus(StatusTurma.valueOf(dto.getStatus().toUpperCase()));
            return turma;
        } else {
            throw new NegocioListException(erros, "Validar campos");
        }
    }

    /*Validar campos para nova turma*/
    private List<String> validarCampos(TurmaDTO dto){
        List<String> erros = new ArrayList<>();
        Optional<Turma> turma = turmasRepository.findByCodigo(dto.getCodigo());
        Optional<Professor> professorCadastro = pesquisarProfessorId(dto.getResponsavelId());

        if(!turma.isEmpty()){
            erros.add("Código de turma já cadastrado.");
        }
        if(professorCadastro.isEmpty()){
            erros.add("Professor não cadastrado.");
        }
        return erros;
    }

    /*Validar campos da trocar status da turma*/
    private List<String> validarCamposTrocaStatus(TrocaStatusTurmaDTO dto, Long idTurma){
        List<String> erros = new ArrayList<>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        } else {
            Optional<Professor> professorResponsavel = pesquisarProfessorId(dto.getIdResponsavel());

            if(professorResponsavel.isEmpty()){
                erros.add("Nenhum professor responsavel encontrado com o id: "+dto.getIdResponsavel());
            } else {
                if(!turma.get().getResponsavelId().getId().equals(dto.getIdResponsavel())){
                    erros.add("Apenas o professor responsável pode alterar o status da turma.");
                }
            }

            if(!StatusTurma.verificarStatus(dto.getStatus())){
                erros.add("Status inserido não existe.");
            }
            if(turma.get().getStatus().toString().equalsIgnoreCase(dto.getStatus())
                && !turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())) {
                erros.add("Essa turma já encontra-se com o status que deseja alterar.");
            } else {
                if (turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())) {
                    erros.add("Você não pode alterar o status da turma que está FINALIZADO.");
                }
            }
        }
        return erros;
    }

    /*metodo para validar troca de responsavel */
    private List<String> validarCamposTrocaResponsavel(TrocaResponsavelTurmaDTO dto, Long idTurma){
        List<String> erros = new ArrayList<>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        } else {

            Optional<Professor> professorResponsavel = pesquisarProfessorId(dto.getIdResponsavel());
            Optional<Professor> novoResponsavel = pesquisarProfessorId(dto.getIdNovoResponsavel());

            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.ARQUIVADO.toString())){
                erros.add("O status da turma está ARQUIVADO. Por favor, ative a turma para trocar de responsável.");
            }
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())){
                erros.add("O status da turma está FINALIZADO. Infelizmente você não pode mais trocar de responsável.");
            }
            if(professorResponsavel.isEmpty()){
                erros.add("Nenhum professor responsavel encontrado com o id: "+dto.getIdResponsavel());
            } else {
                if(!turma.get().getResponsavelId().getId().equals(professorResponsavel.get().getId())){
                    erros.add("Professor com id: "+professorResponsavel.get().getId()+" não é o responsável pela turma.");
                }
            }
            if(novoResponsavel.isEmpty()){
                erros.add("Nenhum professor encontrado com o id: "+dto.getIdNovoResponsavel()+ " para o novo professor responsável.");
            } else {
                int possuiProfessor = turmasRepository.verficarProfessorEmTurma(dto.getIdNovoResponsavel(), idTurma);
                if (possuiProfessor == 0) {
                    erros.add("Esse professor de id: " + dto.getIdNovoResponsavel() + " não está inserido na turma. " +
                            "Insira-o primeiro para depois realizar a troca de responsável.");
                }
            }
        }
        return erros;
    }

    /*metodo para validar turma e professor(es) para remove*/
    private List<String> validarCamposParaProfessorRemove(ProfessorTurmaRemoveListDTO dto, Long idTurma){
        List<String> erros = new ArrayList<String>();
        Optional<Turma> turma = pesquisarTurmaId(idTurma);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        } else {
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.ARQUIVADO.toString())){
                erros.add("O status da turma está ARQUIVADO. Por favor, ative a turma para remover professores.");
            }
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())){
                erros.add("O status da turma está FINALIZADO. Infelizmente você não pode mais remover professores.");
            }
            dto.getListaProfessores().stream()
                    .forEach(idProfessor -> {
                        Optional<Professor> professorRemover = pesquisarProfessorId(idProfessor);

                        if (professorRemover.isEmpty()) {
                            erros.add("Nenhum professor encontrado com o id: " + idProfessor);
                        } else {
                            if (!turma.isEmpty()) {
                                Long professorResponsavel = turma.get().getResponsavelId().getId();
                                if (professorRemover.get().getId().equals(professorResponsavel)) {
                                    erros.add("O professor responsável não pode ser removido. Passe a turma para outro professor para poder ser removido.");
                                } else {
                                    int possuiProfessor = turmasRepository.verficarProfessorEmTurma(idProfessor, idTurma);
                                    if (possuiProfessor == 0) {
                                        erros.add("Esse professor de id: " + idProfessor + " não está inserido na turma.");
                                    }
                                }
                            }
                        }
                    });
        }
        return erros;
    }

    /*metodo para validar turma e professor(es) para insert*/
    private List<String> validarCamposParaProfessorInsert(ProfessorTurmaInsertListDTO dto){
        List<String> erros = new ArrayList<String>();
        Optional<Turma> turma = pesquisarTurmaId(dto.getIdTurma());
        Optional<Professor> professorResponsavel = pesquisarProfessorId(dto.getIdResponsavel());

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        } else {
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.ARQUIVADO.toString())){
                erros.add("O status da turma está ARQUIVADO. Por favor, ative a turma para inserir novos professores.");
            }
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())){
                erros.add("O status da turma está FINALIZADO. Infelizmente você não pode inserir novos professores.");
            }
            if (professorResponsavel.isEmpty()) {
                erros.add("Nenhum professor responsável encontrada com esse id.");
            } else {
                if (!turma.isEmpty()) {
                    if (!turma.get().getResponsavelId().getId().equals(professorResponsavel.get().getId())) {
                        erros.add("Apenas o professor responsável podem inserir professores.");
                    } else {
                        dto.getListaProfessores().stream()
                                .forEach(idProfessor -> {
                                    Optional<Professor> professorCadastro = pesquisarProfessorId(idProfessor);

                                    if (professorCadastro.isEmpty()) {
                                        erros.add("Nenhum professor encontrado com o id: " + idProfessor);
                                    } else {
                                        int possuiProfessor = turmasRepository.verficarProfessorEmTurma(idProfessor, dto.getIdTurma());
                                        if (possuiProfessor > 0) {
                                            erros.add("Esse professor de id: " + idProfessor + " já está inserido na turma.");
                                        }
                                    }
                                });
                    }
                }
            }
        }
        return erros;
    }

    /*metodo para validar turma e aluno(s)*/
    private List<String> validarCamposParaAluno(Long idTurma, Long idProfessor, List<Long> idAlunos, boolean isNewAluno){
        List<String> erros = new ArrayList<String>();
        String tipoCadastro = isNewAluno ? "inserir" : "remover";

        Optional<Turma> turma = pesquisarTurmaId(idTurma);
        Optional<Professor> professor = pesquisarProfessorId(idProfessor);

        if(turma.isEmpty()){
            erros.add("Nenhuma turma encontrada com esse id.");
        } else {
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.ARQUIVADO.toString())){
                erros.add("O status da turma está ARQUIVADO. Por favor, ative a turma para "+tipoCadastro+" novos Alunos.");
            }
            if(turma.get().getStatus().toString().equalsIgnoreCase(StatusTurma.FINALIZADO.toString())){
                erros.add("O status da turma está FINALIZADO. Infelizmente você não pode mais "+tipoCadastro+" Alunos.");
            }
            if (professor.isEmpty()) {
                erros.add("Nenhuma professor encontrado com o id: " + idProfessor);
            } else {
                int professorCadastrado = turmasRepository.verficarProfessorEmTurma(idProfessor, idTurma);

                if (professorCadastrado == 0) {
                    if (isNewAluno) {
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
                        if (isNewAluno) {
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
                .status(turma.getStatus())
                .responsavelId(turma.getResponsavelId().getId())
                .build();
    }

    /*mapeando turmaDTO para turma*/
    private Turma converterTurmaDTO(TurmaDTO dto){
        Optional<Professor> professor = professoresRepository.findById(dto.getResponsavelId());
        return Turma.builder()
                .codigo(dto.getCodigo())
                .periodo(dto.getPeriodo())
                .responsavelId(professor.get())
                .status(dto.getStatus())
                .professores(Arrays.asList(professor.get()))
                .build();
    }

    /*mapeamento de turma para turmaFindDTO, utilizado para pesquisa por turma*/
    private TurmaFindDTO converterTurmaFindDTO(Turma turma) {
        return TurmaFindDTO.builder()
                .id(turma.getId())
                .codigo(turma.getCodigo())
                .periodo(turma.getPeriodo())
                .status(turma.getStatus())
                .responsavel(converterProfessorDTO(turma.getResponsavelId()))
                .alunos(turma.getAlunos().stream()
                        .map(aluno -> converterAlunoDTO(aluno)).collect(Collectors.toList()))
                .professores(turma.getProfessores().stream()
                        .map(professor -> converterProfessorDTO(professor)).collect(Collectors.toList()))
                .build();
    }

    /*metodo para mapear professor para professorDTO*/
    private ProfessorDTO converterProfessorDTO(Professor professor) {
        return ProfessorDTO.builder()
                .id(professor.getId())
                .nome(professor.getNome())
                .email(professor.getEmail())
                .telefone(professor.getTelefone())
                .build();
    }

    /*metodo para mapear aluno para alunoDTO*/
    private AlunoDTO converterAlunoDTO(Aluno aluno) {
        return AlunoDTO.builder()
                .id(aluno.getId())
                .matricula(aluno.getMatricula())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .telefone(aluno.getTelefone())
                .build();
    }
}
