package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.Director.DirectorRequestDTO;
import school_project.example.back.DTO.users.Director.DirectorResponseDTO;
import school_project.example.back.Service.DirectorService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<DirectorResponseDTO> createDirector(
            @Valid @RequestBody DirectorRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(directorService.createDirector(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> getDirector(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                directorService.getDirector(id));
    }

    @GetMapping
    public ResponseEntity<List<DirectorResponseDTO>> getAllDirectors() {

        return ResponseEntity.ok(
                directorService.getAllDirectors());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<DirectorResponseDTO> getByMatricula(
            @PathVariable String matricula) {

        return ResponseEntity.ok(
                directorService.getDirectorByMatricula(matricula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> updateDirector(
            @PathVariable Long id,
            @Valid @RequestBody DirectorRequestDTO dto) {

        DirectorResponseDTO updated = directorService.updateDirector(id, dto);

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/matricula/{matricula}")
    public ResponseEntity<DirectorResponseDTO> updateDirectorByMatricula(
            @PathVariable String matricula,
            @Valid @RequestBody DirectorRequestDTO dto) {

        DirectorResponseDTO updated = directorService.updateDirectorByMatricula(
                matricula,
                dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(
            @PathVariable Long id) {

        directorService.deleteDirector(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/matricula/{matricula}")
    public ResponseEntity<Void> deleteByMatricula(
            @PathVariable String matricula) {

        directorService.deleteDirectorByMatricula(matricula);

        return ResponseEntity.noContent().build();
    }
}