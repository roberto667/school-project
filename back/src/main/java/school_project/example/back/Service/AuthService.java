package school_project.example.back.Service;

import school_project.example.back.DTO.Login.LoginRequestDTO;
import school_project.example.back.DTO.Login.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}