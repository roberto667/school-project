package school_project.example.back.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.Login.LoginRequestDTO;
import school_project.example.back.DTO.Login.LoginResponseDTO;
import school_project.example.back.Service.AuthService;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}