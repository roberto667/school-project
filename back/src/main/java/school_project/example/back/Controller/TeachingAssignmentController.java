package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentRequestDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;
import school_project.example.back.Service.TeachingAssignmentService;

@RestController
@RequestMapping("/api/teaching-assignments")
@RequiredArgsConstructor
public class TeachingAssignmentController {

    private final TeachingAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<TeachingAssignmentResponseDTO> create(
            @Valid @RequestBody TeachingAssignmentRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assignmentService.createAssignment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssignmentResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assignmentService.getAssignment(id));
    }

    @GetMapping
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getAll() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getByTeacher(@PathVariable Long teacherId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsByTeacher(teacherId));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getByClassroom(@PathVariable Long classroomId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsByClassroom(classroomId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<TeachingAssignmentResponseDTO>> getBySubject(@PathVariable Long subjectId) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignmentsBySubject(subjectId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        assignmentService.deleteAssignment(id);

        return ResponseEntity.noContent().build();
    }
}