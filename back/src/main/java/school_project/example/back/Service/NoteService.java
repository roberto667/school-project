package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.NoteDTO;

public interface NoteService {
    NoteDTO createNote(NoteDTO noteDTO);

    NoteDTO getNote(Long noteId);

    List<NoteDTO> getNotesByStudent(Long studentId);

    List<NoteDTO> getAllNotes();

    NoteDTO updateNote(Long noteId, NoteDTO updateNote);

    void deleteNote(Long noteId);
}