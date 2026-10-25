package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeRequestDTO;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeResponseDTO;
import school_project.example.back.Entity.enums.Grau;

public interface SchoolGradeService {

    SchoolGradeResponseDTO createSchoolGrade(
            SchoolGradeRequestDTO dto);

    SchoolGradeResponseDTO getSchoolGrade(Long id);

    SchoolGradeResponseDTO getSchoolGrade(
            Grau grau,
            Integer ano);

    List<SchoolGradeResponseDTO> getAllSchoolGrades();

    SchoolGradeResponseDTO updateSchoolGrade(
            Long id,
            SchoolGradeRequestDTO dto);

    void deleteSchoolGrade(Long id);
}