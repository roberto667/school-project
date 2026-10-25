package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.Teacher.TeacherRequestDTO;
import school_project.example.back.DTO.users.Teacher.TeacherResponseDTO;
import school_project.example.back.Service.TeacherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(
            @Valid @RequestBody TeacherRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teacherService.createTeacher(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacher(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                teacherService.getTeacher(id));
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {

        return ResponseEntity.ok(
                teacherService.getAllTeachers());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<TeacherResponseDTO> getByMatricula(
            @PathVariable String matricula) {

        return ResponseEntity.ok(
                teacherService.getTeacherByMatricula(matricula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequestDTO dto) {

        TeacherResponseDTO updated = teacherService.updateTeacher(id, dto);

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/matricula/{matricula}")
    public ResponseEntity<TeacherResponseDTO> updateTeacherByMatricula(
            @PathVariable String matricula,
            @Valid @RequestBody TeacherRequestDTO dto) {

        TeacherResponseDTO updated = teacherService.updateTeacherByMatricula(
                matricula,
                dto);

        return ResponseEntity.ok(updated);
    }

    // =========================
    // DESATIVAR POR ID
    // =========================

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStudent(
            @PathVariable Long id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // DESATIVAR POR MATRÍCULA
    // =========================

    @PatchMapping("/matricula/{matricula}/deactivate")
    public ResponseEntity<Void> deactivateStudentByMatricula(
            @PathVariable String matricula) {

        teacherService.deleteTeacherByMatricula(matricula);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // REATIVAR POR MATRÍCULA
    // =========================

    @PatchMapping("/matricula/{matricula}/activate")
    public ResponseEntity<Void> activateStudent(
            @PathVariable String matricula) {

        teacherService.activateTeacher(matricula);

        return ResponseEntity.noContent().build();
    }
}