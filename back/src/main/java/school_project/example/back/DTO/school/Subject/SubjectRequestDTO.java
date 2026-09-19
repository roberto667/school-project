package school_project.example.back.DTO.school.Subject;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para cadastrar uma disciplina escolar.
public class SubjectRequestDTO {

    // Nome da disciplina, como Matemática, Português ou História.
    @NotBlank(message = "Nome da disciplina é obrigatório")
    private String name;
}