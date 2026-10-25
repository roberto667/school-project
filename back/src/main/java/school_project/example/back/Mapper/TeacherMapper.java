package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.Teacher.TeacherRequestDTO;
import school_project.example.back.DTO.users.Teacher.TeacherResponseDTO;
import school_project.example.back.Entity.Teacher;

/**
 * Converte a entidade Teacher para DTO e vice-versa,
 * incluindo a matrícula do professor e o usuário associado.
 */
public class TeacherMapper {

    public static TeacherResponseDTO mapToResponseDTO(Teacher teacher) {

        if (teacher == null)
            return null;

        TeacherResponseDTO dto = new TeacherResponseDTO();

        dto.setId(teacher.getId());
        dto.setMatricula(teacher.getMatricula());
        dto.setUser(
                UserMapper.mapToResponseDTO(teacher.getUser()));

        return dto;
    }

    public static Teacher mapToEntity(TeacherRequestDTO dto) {

        if (dto == null)
            return null;

        Teacher teacher = new Teacher();

        teacher.setMatricula(dto.getMatricula());
        teacher.setUser(
                UserMapper.mapToEntity(dto.getUser()));

        return teacher;
    }
}