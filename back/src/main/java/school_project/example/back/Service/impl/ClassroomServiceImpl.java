package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Classroom.ClassroomRequestDTO;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.SchoolGrade;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.ClassroomMapper;
import school_project.example.back.Repository.ClassroomRepository;
import school_project.example.back.Repository.SchoolGradeRepository;
import school_project.example.back.Service.ClassroomService;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final SchoolGradeRepository schoolGradeRepository;

    @Override
    @Transactional
    public ClassroomResponseDTO createClassroom(
            ClassroomRequestDTO dto) {

        SchoolGrade schoolGrade = findSchoolGrade(dto.getSchoolGradeId());

        if (classroomRepository
                .existsByNameIgnoreCaseAndSchoolGradeIdAndAcademicYear(
                        dto.getName(),
                        dto.getSchoolGradeId(),
                        dto.getAcademicYear())) {

            throw new DuplicateResourceException(
                    "Essa turma já existe para essa série e ano letivo.");
        }

        Classroom classroom = ClassroomMapper.mapToEntity(dto, schoolGrade);

        return ClassroomMapper.mapToResponseDTO(
                classroomRepository.save(classroom));
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomResponseDTO getClassroom(Long id) {
        return ClassroomMapper.mapToResponseDTO(findClassroom(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getAllClassrooms() {

        return classroomRepository.findAll()
                .stream()
                .map(ClassroomMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getClassroomsByAcademicYear(
            Integer year) {

        return classroomRepository.findByAcademicYear(year)
                .stream()
                .map(ClassroomMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getClassroomsBySchoolGrade(
            Long schoolGradeId) {

        findSchoolGrade(schoolGradeId);

        return classroomRepository.findBySchoolGradeId(schoolGradeId)
                .stream()
                .map(ClassroomMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDTO> getClassroomsBySchoolGradeAndAcademicYear(
            Long schoolGradeId,
            Integer academicYear) {

        findSchoolGrade(schoolGradeId);

        return classroomRepository
                .findBySchoolGradeIdAndAcademicYear(
                        schoolGradeId,
                        academicYear)
                .stream()
                .map(ClassroomMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ClassroomResponseDTO updateClassroom(
            Long id,
            ClassroomRequestDTO dto) {

        Classroom classroom = findClassroom(id);

        SchoolGrade schoolGrade = findSchoolGrade(dto.getSchoolGradeId());

        boolean changed = !classroom.getName().equalsIgnoreCase(dto.getName())
                || !classroom.getSchoolGrade().getId()
                        .equals(dto.getSchoolGradeId())
                || !classroom.getAcademicYear()
                        .equals(dto.getAcademicYear());

        if (changed
                && classroomRepository
                        .existsByNameIgnoreCaseAndSchoolGradeIdAndAcademicYear(
                                dto.getName(),
                                dto.getSchoolGradeId(),
                                dto.getAcademicYear())) {

            throw new DuplicateResourceException(
                    "Essa turma já existe.");
        }

        classroom.setName(dto.getName());
        classroom.setSchoolGrade(schoolGrade);
        classroom.setAcademicYear(dto.getAcademicYear());

        return ClassroomMapper.mapToResponseDTO(
                classroomRepository.save(classroom));
    }

    @Override
    @Transactional
    public void deleteClassroom(Long id) {
        classroomRepository.delete(findClassroom(id));
    }

    private Classroom findClassroom(Long id) {

        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turma não encontrada com id: " + id));
    }

    private SchoolGrade findSchoolGrade(Long id) {

        return schoolGradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grau escolar não encontrado com id: " + id));
    }
}