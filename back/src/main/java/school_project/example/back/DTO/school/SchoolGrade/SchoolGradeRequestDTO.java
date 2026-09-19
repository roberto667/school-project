package school_project.example.back.DTO.school.SchoolGrade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.Grau;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para cadastrar a etapa escolar da instituição.
public class SchoolGradeRequestDTO {

    // Nível de ensino, como Fundamental II, Médio ou Graduação.
    @NotNull(message = "Grau é obrigatório")
    private Grau grau;

    // Ano referente a essa etapa escolar.
    @NotNull(message = "Ano é obrigatório")
    private Integer ano;
}