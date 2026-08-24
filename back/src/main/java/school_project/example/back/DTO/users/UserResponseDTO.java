package school_project.example.back.DTO.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Para respostas (saída) - sem senha
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
