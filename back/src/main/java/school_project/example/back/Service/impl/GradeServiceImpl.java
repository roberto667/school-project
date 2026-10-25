package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Grade.GradeRequestDTO;
import school_project.example.back.DTO.school.Grade.GradeResponseDTO;
import school_project.example.back.Entity.AcademicPeriod;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.Grade;
import school_project.example.back.Entity.TeachingAssignment;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.GradeMapper;
import school_project.example.back.Repository.AcademicPeriodRepository;
import school_project.example.back.Repository.EnrollmentRepository;
import school_project.example.back.Repository.GradeRepository;
import school_project.example.back.Repository.TeachingAssignmentRepository;
import school_project.example.back.Service.GradeService;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingAssignmentRepository assignmentRepository;
    private final AcademicPeriodRepository periodRepository;

    @Override
    @Transactional
    public GradeResponseDTO createGrade(GradeRequestDTO dto) {

        Enrollment enrollment = findEnrollment(dto.getEnrollmentId());

        TeachingAssignment assignment = findAssignment(dto.getTeachingAssignmentId());

        AcademicPeriod period = findPeriod(dto.getPeriodId());

        validateClassroom(enrollment, assignment);

        if (gradeRepository
                .existsByEnrollmentIdAndTeachingAssignmentIdAndPeriodId(
                        enrollment.getId(),
                        assignment.getId(),
                        period.getId())) {

            throw new DuplicateResourceException(
                    "Já existe uma nota para esse aluno, disciplina e período.");
        }

        Grade grade = GradeMapper.mapToEntity(
                dto,
                enrollment,
                assignment,
                period);

        return GradeMapper.mapToResponseDTO(
                gradeRepository.save(grade));
    }

    @Override
    @Transactional(readOnly = true)
    public GradeResponseDTO getGrade(Long id) {
        return GradeMapper.mapToResponseDTO(findGrade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getAllGrades() {

        return gradeRepository.findAll()
                .stream()
                .map(GradeMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByEnrollment(
            Long enrollmentId) {

        return gradeRepository.findByEnrollmentId(enrollmentId)
                .stream()
                .map(GradeMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByTeachingAssignment(
            Long assignmentId) {

        return gradeRepository
                .findByTeachingAssignmentId(assignmentId)
                .stream()
                .map(GradeMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByEnrollmentAndTeachingAssignment(
            Long enrollmentId,
            Long assignmentId) {

        return gradeRepository
                .findByEnrollmentIdAndTeachingAssignmentId(
                        enrollmentId,
                        assignmentId)
                .stream()
                .map(GradeMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public GradeResponseDTO updateGrade(
            Long gradeId,
            GradeRequestDTO dto) {

        Grade grade = findGrade(gradeId);

        Enrollment enrollment = findEnrollment(dto.getEnrollmentId());

        TeachingAssignment assignment = findAssignment(dto.getTeachingAssignmentId());

        AcademicPeriod period = findPeriod(dto.getPeriodId());

        validateClassroom(enrollment, assignment);

        gradeRepository
                .findByEnrollmentIdAndTeachingAssignmentIdAndPeriodId(
                        enrollment.getId(),
                        assignment.getId(),
                        period.getId())
                .filter(existing -> !existing.getId().equals(gradeId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Já existe uma nota para esse aluno, disciplina e período.");
                });

        grade.setEnrollment(enrollment);
        grade.setTeachingAssignment(assignment);
        grade.setPeriod(period);
        grade.setValue(dto.getValue());
        grade.setRecoveryValue(dto.getRecoveryValue());

        return GradeMapper.mapToResponseDTO(
                gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public void deleteGrade(Long id) {
        gradeRepository.delete(findGrade(id));
    }

    private void validateClassroom(
            Enrollment enrollment,
            TeachingAssignment assignment) {

        if (!enrollment.getClassroom().getId()
                .equals(assignment.getClassroom().getId())) {

            throw new InvalidDataException(
                    "O aluno não pertence à turma dessa alocação.");
        }
    }

    private Grade findGrade(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nota não encontrada com id: " + id));
    }

    private Enrollment findEnrollment(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matrícula escolar não encontrada."));
    }

    private TeachingAssignment findAssignment(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alocação do professor não encontrada."));
    }

    private AcademicPeriod findPeriod(Long id) {
        return periodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Período acadêmico não encontrado."));
    }
}