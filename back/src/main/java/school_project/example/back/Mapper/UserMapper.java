package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.User.UserRequestDTO;
import school_project.example.back.DTO.users.User.UserResponseDTO;
import school_project.example.back.Entity.User;

/**
 * Converte a entidade User para DTO e vice-versa,
 * mapeando os dados básicos do usuário e sua autenticação.
 */
public class UserMapper {

    public static UserResponseDTO mapToResponseDTO(User user) {

        if (user == null)
            return null;

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());


        return dto;
    }

    public static User mapToEntity(UserRequestDTO dto) {

        if (dto == null)
            return null;

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setActive(true);

        return user;
    }
}