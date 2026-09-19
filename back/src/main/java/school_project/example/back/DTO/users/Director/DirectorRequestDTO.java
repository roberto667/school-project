package school_project.example.back.DTO.users.Director;

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
// DTO usado para criar o perfil de um diretor junto com a conta de usuário.
public class DirectorRequestDTO {

    // Matrícula funcional do diretor.
    @NotBlank(message = "Matrícula é obrigatória")
    private String matricula;

    // Dados de usuário vinculados ao diretor.
    @Valid
    @NotNull(message = "Usuário é obrigatório")
    private UserRequestDTO user;
}