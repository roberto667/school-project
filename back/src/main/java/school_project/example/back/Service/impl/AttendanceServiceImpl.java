package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Attendance.AttendanceRequestDTO;
import school_project.example.back.DTO.school.Attendance.AttendanceResponseDTO;
import school_project.example.back.Entity.Attendance;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.TeachingAssignment;
import school_project.example.back.Entity.enums.AttendanceStatus;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.AttendanceMapper;
import school_project.example.back.Repository.AttendanceRepository;
import school_project.example.back.Repository.EnrollmentRepository;
import school_project.example.back.Repository.TeachingAssignmentRepository;
import school_project.example.back.Service.AttendanceService;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingAssignmentRepository assignmentRepository;

    @Override
    @Transactional
    public AttendanceResponseDTO createAttendance(
            AttendanceRequestDTO dto) {

        Enrollment enrollment = findEnrollment(dto.getEnrollmentId());

        TeachingAssignment assignment = findAssignment(dto.getTeachingAssignmentId());

        validateClassroom(enrollment, assignment);

        if (attendanceRepository
                .existsByEnrollmentIdAndTeachingAssignmentIdAndDate(
                        enrollment.getId(),
                        assignment.getId(),
                        dto.getDate())) {

            throw new DuplicateResourceException(
                    "Já existe chamada para esse aluno, disciplina e data.");
        }

        Attendance attendance = AttendanceMapper.mapToEntity(
                dto,
                enrollment,
                assignment);

        return AttendanceMapper.mapToResponseDTO(
                attendanceRepository.save(attendance));
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponseDTO getAttendance(Long id) {
        return AttendanceMapper.mapToResponseDTO(findAttendance(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAllAttendances() {

        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendancesByEnrollment(
            Long enrollmentId) {

        return attendanceRepository.findByEnrollmentId(enrollmentId)
                .stream()
                .map(AttendanceMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendancesByTeachingAssignment(
            Long assignmentId) {

        return attendanceRepository
                .findByTeachingAssignmentId(assignmentId)
                .stream()
                .map(AttendanceMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendancesByEnrollmentAndTeachingAssignment(
            Long enrollmentId,
            Long assignmentId) {

        return attendanceRepository
                .findByEnrollmentIdAndTeachingAssignmentId(
                        enrollmentId,
                        assignmentId)
                .stream()
                .map(AttendanceMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEnrollmentAndStatus(
            Long enrollmentId,
            AttendanceStatus status) {

        return attendanceRepository
                .countByEnrollmentIdAndStatus(
                        enrollmentId,
                        status);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEnrollmentAndTeachingAssignmentAndStatus(
            Long enrollmentId,
            Long assignmentId,
            AttendanceStatus status) {

        return attendanceRepository
                .countByEnrollmentIdAndTeachingAssignmentIdAndStatus(
                        enrollmentId,
                        assignmentId,
                        status);
    }

    @Override
    @Transactional
    public AttendanceResponseDTO updateAttendance(
            Long attendanceId,
            AttendanceRequestDTO dto) {

        Attendance attendance = findAttendance(attendanceId);

        Enrollment enrollment = findEnrollment(dto.getEnrollmentId());

        TeachingAssignment assignment = findAssignment(dto.getTeachingAssignmentId());

        validateClassroom(enrollment, assignment);

        attendanceRepository
                .findByEnrollmentIdAndTeachingAssignmentIdAndDate(
                        enrollment.getId(),
                        assignment.getId(),
                        dto.getDate())
                .filter(existing -> !existing.getId().equals(attendanceId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Já existe chamada para esse aluno, disciplina e data.");
                });

        attendance.setEnrollment(enrollment);
        attendance.setTeachingAssignment(assignment);
        attendance.setDate(dto.getDate());
        attendance.setStatus(dto.getStatus());

        return AttendanceMapper.mapToResponseDTO(
                attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public void deleteAttendance(Long id) {
        attendanceRepository.delete(findAttendance(id));
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

    private Attendance findAttendance(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Registro de presença não encontrado."));
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
}