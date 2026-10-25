package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Attendance.AttendanceRequestDTO;
import school_project.example.back.DTO.school.Attendance.AttendanceResponseDTO;
import school_project.example.back.Entity.enums.AttendanceStatus;
import school_project.example.back.Service.AttendanceService;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponseDTO> create(
            @Valid @RequestBody AttendanceRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendanceService.createAttendance(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendance(id));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAll() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendances());
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByEnrollment(
            @PathVariable Long enrollmentId) {

        return ResponseEntity.ok(
                attendanceService
                        .getAttendancesByEnrollment(enrollmentId));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByAssignment(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                attendanceService
                        .getAttendancesByTeachingAssignment(
                                assignmentId));
    }

    @GetMapping("/enrollment/{enrollmentId}/assignment/{assignmentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByEnrollmentAndAssignment(
            @PathVariable Long enrollmentId,
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                attendanceService
                        .getAttendancesByEnrollmentAndTeachingAssignment(
                                enrollmentId,
                                assignmentId));
    }

    @GetMapping("/enrollment/{enrollmentId}/count")
    public ResponseEntity<Long> count(
            @PathVariable Long enrollmentId,
            @RequestParam AttendanceStatus status) {

        return ResponseEntity.ok(
                attendanceService
                        .countByEnrollmentAndStatus(
                                enrollmentId,
                                status));
    }

    @GetMapping("/enrollment/{enrollmentId}/assignment/{assignmentId}/count")
    public ResponseEntity<Long> countByAssignment(
            @PathVariable Long enrollmentId,
            @PathVariable Long assignmentId,
            @RequestParam AttendanceStatus status) {

        return ResponseEntity.ok(
                attendanceService
                        .countByEnrollmentAndTeachingAssignmentAndStatus(
                                enrollmentId,
                                assignmentId,
                                status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequestDTO dto) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.noContent().build();
    }
}