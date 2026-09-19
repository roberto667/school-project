package school_project.example.back.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import school_project.example.back.DTO.Grade.NoteDTO;
import school_project.example.back.Entity.Note;
import school_project.example.back.Entity.Student;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.NoteMapper;
import school_project.example.back.Repository.NoteRepository;
import school_project.example.back.Repository.StudentRepository;
import school_project.example.back.Service.NoteService;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final StudentRepository studentRepository;

    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {
        Student student = studentRepository.findById(noteDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id " + noteDTO.getStudentId()));

        Note boletim = NoteMapper.maptoBoletim(noteDTO, student);
        Note saved = noteRepository.save(boletim);
        return NoteMapper.maptoBoletimDTO(saved);
    }

    @Override
    public NoteDTO getNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + noteId));
        return NoteMapper.maptoBoletimDTO(note);
    }

    @Override
    public List<NoteDTO> getNotesByStudent(Long studentId) {
        List<Note> notes = noteRepository.findByStudentId(studentId);
        return notes.stream()
                .map(NoteMapper::maptoBoletimDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(NoteMapper::maptoBoletimDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO updateNote(Long noteId, NoteDTO updateNote) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + noteId));

        note.setDisciplina(updateNote.getDisciplina());
        note.setAnoLetivo(updateNote.getAnoLetivo());
        note.setNota1(updateNote.getNota1());
        note.setNota2(updateNote.getNota2());
        note.setNota3(updateNote.getNota3());
        note.setNota4(updateNote.getNota4());
        note.setRec1(updateNote.getRec1());
        note.setRec2(updateNote.getRec2());
        note.setRec3(updateNote.getRec3());
        note.setRec4(updateNote.getRec4());
        note.setRecFinal(updateNote.getRecFinal());

        Note saved = noteRepository.save(note);
        return NoteMapper.maptoBoletimDTO(saved);
    }

    @Override
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + noteId));
        noteRepository.delete(note);
    }
}