package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.User.UserResponseDTO;
import school_project.example.back.DTO.users.User.UserUpdateDTO;

public interface UserService {

    UserResponseDTO getUser(Long userId);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(
            Long userId,
            UserUpdateDTO dto);

    void deleteUser(Long userId);
}