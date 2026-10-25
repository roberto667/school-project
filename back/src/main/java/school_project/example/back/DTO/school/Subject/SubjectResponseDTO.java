package school_project.example.back.DTO.school.Subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com os dados de uma disciplina.
public class SubjectResponseDTO {

    // Identificador único da disciplina.
    private Long id;

    // Nome da disciplina cadastrada.
    private String nome;
}