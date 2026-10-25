package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.Student.StudentResponseDTO;
import school_project.example.back.Service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // =========================
    // CREATE
    // =========================

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO dto) {

        StudentResponseDTO created = studentService.createStudent(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // =========================
    // GET TODOS
    // =========================

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents());
    }

    // =========================
    // GET POR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentService.getStudent(id));
    }

    // =========================
    // GET POR MATRÍCULA
    // =========================

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<StudentResponseDTO> getByMatricula(
            @PathVariable String matricula) {

        return ResponseEntity.ok(
                studentService.getStudentByMatricula(matricula));
    }

    // =========================
    // UPDATE POR ID
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO dto) {

        StudentResponseDTO updated = studentService.updateStudent(id, dto);

        return ResponseEntity.ok(updated);
    }

    // =========================
    // UPDATE POR MATRÍCULA
    // =========================

    @PutMapping("/matricula/{matricula}")
    public ResponseEntity<StudentResponseDTO> updateStudentByMatricula(
            @PathVariable String matricula,
            @Valid @RequestBody StudentRequestDTO dto) {

        StudentResponseDTO updated = studentService.updateStudentByMatricula(
                matricula, dto);

        return ResponseEntity.ok(updated);
    }

    // =========================
    // DESATIVAR POR ID
    // =========================

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // DESATIVAR POR MATRÍCULA
    // =========================

    @PatchMapping("/matricula/{matricula}/deactivate")
    public ResponseEntity<Void> deactivateStudentByMatricula(
            @PathVariable String matricula) {

        studentService.deleteStudentByMatricula(matricula);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // REATIVAR POR MATRÍCULA
    // =========================

    @PatchMapping("/matricula/{matricula}/activate")
    public ResponseEntity<Void> activateStudent(
            @PathVariable String matricula) {

        studentService.activateStudent(matricula);

        return ResponseEntity.noContent().build();
    }
}