package school_project.example.back.DTO.users.User;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String firstName;

    private String lastName;

    @Email(message = "Email inválido")
    private String email;
}