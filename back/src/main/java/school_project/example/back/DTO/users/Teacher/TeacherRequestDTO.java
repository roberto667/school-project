package school_project.example.back.DTO.users.Teacher;

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
// DTO usado para criar o perfil de um professor junto com a conta de usuário.
public class TeacherRequestDTO {

    // Matrícula funcional do professor.
    @NotBlank(message = "Matrícula é obrigatória")
    private String matricula;

    // Dados de usuário vinculados ao professor.
    @Valid
    @NotNull(message = "Usuário é obrigatório")
    private UserRequestDTO user;
}