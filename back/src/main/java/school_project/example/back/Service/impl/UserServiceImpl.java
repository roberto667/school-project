package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.User.UserResponseDTO;
import school_project.example.back.DTO.users.User.UserUpdateDTO;
import school_project.example.back.Entity.User;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.UserMapper;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long userId) {

        User user = findUser(userId);

        return UserMapper.mapToResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(
            Long userId,
            UserUpdateDTO dto) {

        User user = findUser(userId);

        if (dto.getEmail() != null
                && !dto.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException(
                    "Já existe um usuário com esse email.");
        }

        if (dto.getFirstName() != null
                && !dto.getFirstName().isBlank()) {
            user.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null
                && !dto.getLastName().isBlank()) {
            user.setLastName(dto.getLastName());
        }

        if (dto.getEmail() != null
                && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        User saved = userRepository.save(user);

        return UserMapper.mapToResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        User user = findUser(userId);

        userRepository.delete(user);
    }

    private User findUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com id: " + userId));
    }
}