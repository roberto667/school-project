package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentRequestDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.Subject;
import school_project.example.back.Entity.Teacher;
import school_project.example.back.Entity.TeachingAssignment;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.TeachingAssignmentMapper;
import school_project.example.back.Repository.ClassroomRepository;
import school_project.example.back.Repository.SubjectRepository;
import school_project.example.back.Repository.TeacherRepository;
import school_project.example.back.Repository.TeachingAssignmentRepository;
import school_project.example.back.Service.TeachingAssignmentService;

@Service
@RequiredArgsConstructor
public class TeachingAssignmentServiceImpl
        implements TeachingAssignmentService {

    private final TeachingAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public TeachingAssignmentResponseDTO createAssignment(
            TeachingAssignmentRequestDTO dto) {

        Teacher teacher = teacherRepository
                .findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor não encontrado."));

        Classroom classroom = classroomRepository
                .findById(dto.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turma não encontrada."));

        Subject subject = subjectRepository
                .findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Disciplina não encontrada."));

        if (assignmentRepository
                .existsByTeacherIdAndClassroomIdAndSubjectId(
                        teacher.getId(),
                        classroom.getId(),
                        subject.getId())) {

            throw new DuplicateResourceException(
                    "Esse professor já está associado a essa disciplina nessa turma.");
        }

        TeachingAssignment assignment = TeachingAssignmentMapper.mapToEntity(
                dto,
                teacher,
                classroom,
                subject);

        return TeachingAssignmentMapper.mapToResponseDTO(
                assignmentRepository.save(assignment));
    }

    @Override
    @Transactional(readOnly = true)
    public TeachingAssignmentResponseDTO getAssignment(Long id) {
        return TeachingAssignmentMapper.mapToResponseDTO(findAssignment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeachingAssignmentResponseDTO> getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(TeachingAssignmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeachingAssignmentResponseDTO> getAssignmentsByTeacher(
            Long teacherId) {

        return assignmentRepository.findByTeacherId(teacherId)
                .stream()
                .map(TeachingAssignmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeachingAssignmentResponseDTO> getAssignmentsByClassroom(
            Long classroomId) {

        return assignmentRepository.findByClassroomId(classroomId)
                .stream()
                .map(TeachingAssignmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeachingAssignmentResponseDTO> getAssignmentsBySubject(
            Long subjectId) {

        return assignmentRepository.findBySubjectId(subjectId)
                .stream()
                .map(TeachingAssignmentMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        assignmentRepository.delete(findAssignment(id));
    }

    private TeachingAssignment findAssignment(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alocação não encontrada com id: " + id));
    }
}