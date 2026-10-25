package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodRequestDTO;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodResponseDTO;

public interface AcademicPeriodService {

    AcademicPeriodResponseDTO createPeriod(
            AcademicPeriodRequestDTO dto);

    AcademicPeriodResponseDTO getPeriod(Long periodId);

    AcademicPeriodResponseDTO getPeriodByNumber(
            Integer number);

    List<AcademicPeriodResponseDTO> getAllPeriods();

    AcademicPeriodResponseDTO updatePeriod(
            Long periodId,
            AcademicPeriodRequestDTO dto);

    void deletePeriod(Long periodId);
}