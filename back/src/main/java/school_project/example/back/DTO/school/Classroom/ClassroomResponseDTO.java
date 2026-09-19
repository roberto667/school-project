package school_project.example.back.DTO.school.Classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta contendo os dados de uma turma.
public class ClassroomResponseDTO {

    // Identificador único da turma.
    private Long id;

    // Nome da turma.
    private String name;

    // Série escolar da turma.
    private SchoolGradeResponseDTO schoolGrade;

    // Ano letivo da turma.
    private Integer academicYear;
}