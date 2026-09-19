package school_project.example.back.DTO.users.Student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.users.User.UserRequestDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para criar o perfil de um aluno junto com os dados do usuário.
public class StudentRequestDTO {

    // Número de matrícula do aluno na instituição.
    @NotBlank(message = "Matrícula é obrigatória")
    private String matricula;

    // Dados de cadastro do usuário responsável pela conta do aluno.
    @Valid
    @NotNull(message = "Usuário é obrigatório")
    private UserRequestDTO user;
}