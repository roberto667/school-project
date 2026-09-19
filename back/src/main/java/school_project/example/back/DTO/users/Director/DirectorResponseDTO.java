package school_project.example.back.DTO.users.Director;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.users.User.UserResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com os dados do diretor e do usuário associado.
public class DirectorResponseDTO {

    // Identificador único do diretor.
    private Long id;

    // Matrícula do diretor na instituição.
    private String matricula;

    // Dados do usuário vinculado ao diretor.
    private UserResponseDTO user;
}