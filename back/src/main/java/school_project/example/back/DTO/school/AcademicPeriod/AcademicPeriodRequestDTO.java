package school_project.example.back.DTO.school.AcademicPeriod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para cadastrar ou editar um período acadêmico.
public class AcademicPeriodRequestDTO {

    // Nome do período, como "1º Semestre" ou "2026".
    @NotBlank(message = "Nome do período é obrigatório")
    private String name;

    // Número ou ordem do período dentro do calendário acadêmico.
    @NotNull(message = "Número do período é obrigatório")
    private Integer number;
}