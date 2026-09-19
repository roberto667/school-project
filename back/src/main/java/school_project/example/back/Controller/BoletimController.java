package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import school_project.example.back.DTO.Grade.NoteDTO;
import school_project.example.back.Service.NoteService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/boletins")
public class BoletimController {

    private final NoteService boletimService;

    @PostMapping
    public ResponseEntity<NoteDTO> createBoletim(@RequestBody NoteDTO noteDTO) {
        NoteDTO created = boletimService.createNote(noteDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable Long id) {
        return ResponseEntity.ok(boletimService.getNote(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<NoteDTO>> getNoteByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(boletimService.getNotesByStudent(studentId));
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(boletimService.getAllNotes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNotes(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(boletimService.updateNote(id, noteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoletim(@PathVariable Long id) {
        boletimService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}