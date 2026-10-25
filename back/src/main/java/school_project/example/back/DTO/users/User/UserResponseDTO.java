package school_project.example.back.DTO.users.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para retornos de usuário sem expor a senha.
public class UserResponseDTO {
    // Identificador único do usuário.
    private Long id;

    // Primeiro nome do usuário.
    private String firstName;

    // Sobrenome do usuário.
    private String lastName;

    // E-mail cadastrado do usuário.
    private String email;
    private Boolean active;
}
