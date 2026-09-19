package school_project.example.back.DTO.users.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para criar ou atualizar os dados básicos de um usuário.
public class UserRequestDTO {
    // Primeiro nome do usuário.
    @NotBlank(message = "Nome é obrigatório")
    private String firstName;

    // Sobrenome do usuário.
    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;

    // E-mail principal do usuário, usado como identificador de login.
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // Senha para acesso ao sistema. Deve ter ao menos 8 caracteres.
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;
}