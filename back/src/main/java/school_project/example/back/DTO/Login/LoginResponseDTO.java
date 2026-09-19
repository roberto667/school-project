package school_project.example.back.DTO.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.users.User.UserResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de retorno após a autenticação bem-sucedida.
public class LoginResponseDTO {
    // Token gerado para manter a sessão autenticada.
    private String token;

    // Dados do usuário autenticado, sem expor a senha.
    private UserResponseDTO user;

    // Papel do usuário no sistema, como STUDENT, TEACHER ou NONE.
    private String role; // "STUDENT", "TEACHER" ou "NONE"
}