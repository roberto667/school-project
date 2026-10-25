package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodRequestDTO;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodResponseDTO;
import school_project.example.back.Entity.AcademicPeriod;

/**
 * Converte a entidade AcademicPeriod para DTO e vice-versa,
 * permitindo criar e consultar períodos letivos da escola.
 */
public class AcademicPeriodMapper {

    public static AcademicPeriodResponseDTO mapToResponseDTO(
            AcademicPeriod period) {

        if (period == null)
            return null;

        AcademicPeriodResponseDTO dto = new AcademicPeriodResponseDTO();

        dto.setId(period.getId());
        dto.setName(period.getName());
        dto.setNumber(period.getNumber());

        return dto;
    }

    public static AcademicPeriod mapToEntity(
            AcademicPeriodRequestDTO dto) {

        if (dto == null)
            return null;

        AcademicPeriod period = new AcademicPeriod();

        period.setName(dto.getName());
        period.setNumber(dto.getNumber());

        return period;
    }
}