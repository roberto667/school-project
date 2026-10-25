package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Enrollment.EnrollmentRequestDTO;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.Entity.enums.EnrollmentStatus;
import school_project.example.back.Service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> create(
            @Valid @RequestBody EnrollmentRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(enrollmentService.createEnrollment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollment(id));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> getAll() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentsByStudent(studentId));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getByClassroom(
            @PathVariable Long classroomId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentsByClassroom(classroomId));
    }

    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getByStudentAndStatus(
            @PathVariable Long studentId,
            @PathVariable EnrollmentStatus status) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentsByStudentAndStatus(
                                studentId,
                                status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EnrollmentStatus status) {

        return ResponseEntity.ok(
                enrollmentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        enrollmentService.deleteEnrollment(id);

        return ResponseEntity.noContent().build();
    }
}