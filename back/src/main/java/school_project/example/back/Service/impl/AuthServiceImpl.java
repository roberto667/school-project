package school_project.example.back.Service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.Login.LoginRequestDTO;
import school_project.example.back.DTO.Login.LoginResponseDTO;
import school_project.example.back.Entity.User;
import school_project.example.back.Exception.InvalidCredentialsException;
import school_project.example.back.Mapper.UserMapper;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Security.CustomUserDetailsService;
import school_project.example.back.Security.JwtService;
import school_project.example.back.Service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()));

        } catch (BadCredentialsException ex) {

            throw new InvalidCredentialsException(
                    "Email ou senha inválidos.");
        }

        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Email ou senha inválidos."));

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                user.getEmail());

        String token = jwtService.generateToken(userDetails);

        String role;

        if (user.getDirector() != null) {
            role = "DIRECTOR";
        } else if (user.getTeacher() != null) {
            role = "TEACHER";
        } else if (user.getStudent() != null) {
            role = "STUDENT";
        } else {
            throw new InvalidCredentialsException(
                    "Usuário não possui perfil associado.");
        }

        LoginResponseDTO response = new LoginResponseDTO();

        response.setToken(token);
        response.setUser(
                UserMapper.mapToResponseDTO(user));
        response.setRole(role);

        return response;
    }
}