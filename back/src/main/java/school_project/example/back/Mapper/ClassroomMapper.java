package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.Classroom.ClassroomRequestDTO;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.SchoolGrade;

/**
 * Converte a entidade Classroom para DTO e vice-versa,
 * mapeando dados da turma e do nível escolar relacionado.
 */
public class ClassroomMapper {

    public static ClassroomResponseDTO mapToResponseDTO(
            Classroom classroom) {

        if (classroom == null)
            return null;

        ClassroomResponseDTO dto = new ClassroomResponseDTO();

        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        dto.setAcademicYear(classroom.getAcademicYear());

        dto.setSchoolGrade(
                SchoolGradeMapper.mapToResponseDTO(
                        classroom.getSchoolGrade()));

        return dto;
    }

    public static Classroom mapToEntity(
            ClassroomRequestDTO dto,
            SchoolGrade schoolGrade) {

        if (dto == null)
            return null;

        Classroom classroom = new Classroom();

        classroom.setName(dto.getName());
        classroom.setAcademicYear(dto.getAcademicYear());
        classroom.setSchoolGrade(schoolGrade);

        return classroom;
    }
}