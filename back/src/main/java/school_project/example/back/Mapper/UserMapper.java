package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.User.UserRequestDTO;
import school_project.example.back.DTO.users.User.UserResponseDTO;
import school_project.example.back.Entity.User;

public class UserMapper {
    public static UserRequestDTO maptoUserRequestDTO(User user) {
        if (user == null)
            return null;
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        // geralmente NÃO se retorna a senha em DTOs de saída
        return dto;

    }

    public static UserResponseDTO maptoUserResponseDTO(User user) {
        if (user == null)
            return null;
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User maptoUser(UserRequestDTO userDTO) {
        if (userDTO == null)
            return null;
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

}
