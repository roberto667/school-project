package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Enrollment.EnrollmentRequestDTO;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.Student;
import school_project.example.back.Entity.enums.EnrollmentStatus;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.EnrollmentMapper;
import school_project.example.back.Repository.ClassroomRepository;
import school_project.example.back.Repository.EnrollmentRepository;
import school_project.example.back.Repository.StudentRepository;
import school_project.example.back.Service.EnrollmentService;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    @Transactional
    public EnrollmentResponseDTO createEnrollment(
            EnrollmentRequestDTO dto) {

        Student student = studentRepository
                .findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aluno não encontrado."));

        Classroom classroom = classroomRepository
                .findById(dto.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turma não encontrada."));

        if (enrollmentRepository.existsByStudentIdAndClassroomId(
                student.getId(),
                classroom.getId())) {

            throw new DuplicateResourceException(
                    "O aluno já possui matrícula nessa turma.");
        }

        if (enrollmentRepository.existsByStudentIdAndStatus(
                student.getId(),
                EnrollmentStatus.ACTIVE)) {

            throw new DuplicateResourceException(
                    "O aluno já possui uma matrícula ativa.");
        }

        Enrollment enrollment = EnrollmentMapper.mapToEntity(
                dto,
                student,
                classroom);

        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        return EnrollmentMapper.mapToResponseDTO(
                enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentResponseDTO getEnrollment(Long id) {
        return EnrollmentMapper.mapToResponseDTO(findEnrollment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getAllEnrollments() {

        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsByStudent(
            Long studentId) {

        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(EnrollmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsByClassroom(
            Long classroomId) {

        return enrollmentRepository.findByClassroomId(classroomId)
                .stream()
                .map(EnrollmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsByStudentAndStatus(
            Long studentId,
            EnrollmentStatus status) {

        return enrollmentRepository
                .findByStudentIdAndStatus(studentId, status)
                .stream()
                .map(EnrollmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public EnrollmentResponseDTO updateStatus(
            Long id,
            EnrollmentStatus status) {

        Enrollment enrollment = findEnrollment(id);

        enrollment.setStatus(status);

        return EnrollmentMapper.mapToResponseDTO(
                enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void deleteEnrollment(Long id) {
        enrollmentRepository.delete(findEnrollment(id));
    }

    private Enrollment findEnrollment(Long id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matrícula escolar não encontrada com id: " + id));
    }
}