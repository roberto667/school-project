package school_project.example.back.DTO.school.SchoolGrade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.Grau;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com a etapa escolar cadastrada.
public class SchoolGradeResponseDTO {

    // Identificador único da etapa escolar.
    private Long id;

    // Grau ou nível de ensino associado.
    private Grau grau;

    // Ano da etapa escolar.
    private Integer ano;
}