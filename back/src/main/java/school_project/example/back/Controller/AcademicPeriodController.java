package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodRequestDTO;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodResponseDTO;
import school_project.example.back.Service.AcademicPeriodService;

@RestController
@RequestMapping("/api/academic-periods")
@RequiredArgsConstructor
public class AcademicPeriodController {

    private final AcademicPeriodService periodService;

    @PostMapping
    public ResponseEntity<AcademicPeriodResponseDTO> create(
            @Valid @RequestBody AcademicPeriodRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(periodService.createPeriod(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicPeriodResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                periodService.getPeriod(id));
    }

    @GetMapping
    public ResponseEntity<List<AcademicPeriodResponseDTO>> getAll() {

        return ResponseEntity.ok(
                periodService.getAllPeriods());
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<AcademicPeriodResponseDTO> getByNumber(
            @PathVariable Integer number) {

        return ResponseEntity.ok(
                periodService.getPeriodByNumber(number));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicPeriodResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AcademicPeriodRequestDTO dto) {

        return ResponseEntity.ok(
                periodService.updatePeriod(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        periodService.deletePeriod(id);

        return ResponseEntity.noContent().build();
    }
}