package school_project.example.back.Service;

import school_project.example.back.DTO.LoginRequestDTO;
import school_project.example.back.DTO.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}