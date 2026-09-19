package school_project.example.back.DTO.school.AcademicPeriod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com os dados de um período acadêmico.
public class AcademicPeriodResponseDTO {

    // Identificador único do período.
    private Long id;

    // Nome do período acadêmico.
    private String name;

    // Ordem ou número do período acadêmico.
    private Integer number;
}