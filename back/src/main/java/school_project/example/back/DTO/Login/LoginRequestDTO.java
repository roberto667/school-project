package school_project.example.back.DTO.Login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para receber os dados enviados no login do usuário.
public class LoginRequestDTO {

    // E-mail informado pelo usuário para autenticação.
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // Senha digitada pelo usuário para validar o login.
    @NotBlank(message = "Senha é obrigatória")
    private String password;
}