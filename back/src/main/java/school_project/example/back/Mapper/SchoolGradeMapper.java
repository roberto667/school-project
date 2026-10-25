package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeRequestDTO;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeResponseDTO;
import school_project.example.back.Entity.SchoolGrade;

/**
 * Converte a entidade SchoolGrade para DTO e vice-versa,
 * representando os dados do nível ou etapa escolar.
 */
public class SchoolGradeMapper {

    public static SchoolGradeResponseDTO mapToResponseDTO(
            SchoolGrade schoolGrade) {

        if (schoolGrade == null)
            return null;

        SchoolGradeResponseDTO dto = new SchoolGradeResponseDTO();

        dto.setId(schoolGrade.getId());
        dto.setGrau(schoolGrade.getGrau());
        dto.setAno(schoolGrade.getAno());

        return dto;
    }

    public static SchoolGrade mapToEntity(
            SchoolGradeRequestDTO dto) {

        if (dto == null)
            return null;

        SchoolGrade schoolGrade = new SchoolGrade();

        schoolGrade.setGrau(dto.getGrau());
        schoolGrade.setAno(dto.getAno());

        return schoolGrade;
    }
}