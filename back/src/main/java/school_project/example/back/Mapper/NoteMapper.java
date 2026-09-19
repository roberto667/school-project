package school_project.example.back.Mapper;

import school_project.example.back.DTO.Grade.NoteDTO;
import school_project.example.back.Entity.Note;
import school_project.example.back.Entity.Student;

public class NoteMapper {

    public static NoteDTO maptoBoletimDTO(Note boletim) {
        if (boletim == null)
            return null;

        NoteDTO dto = new NoteDTO();
        dto.setId(boletim.getId());
        dto.setStudentId(boletim.getStudent().getId());
        dto.setDisciplina(boletim.getDisciplina());
        dto.setAnoLetivo(boletim.getAnoLetivo());
        dto.setNota1(boletim.getNota1());
        dto.setNota2(boletim.getNota2());
        dto.setNota3(boletim.getNota3());
        dto.setNota4(boletim.getNota4());
        dto.setRec1(boletim.getRec1());
        dto.setRec2(boletim.getRec2());
        dto.setRec3(boletim.getRec3());
        dto.setRec4(boletim.getRec4());
        dto.setRecFinal(boletim.getRecFinal());

        return dto;
    }

    // recebe o Student já buscado pelo service, porque o DTO só tem o id
    public static Note maptoBoletim(NoteDTO dto, Student student) {
        if (dto == null)
            return null;

        Note boletim = new Note();
        boletim.setId(dto.getId());
        boletim.setStudent(student);
        boletim.setDisciplina(dto.getDisciplina());
        boletim.setAnoLetivo(dto.getAnoLetivo());
        boletim.setNota1(dto.getNota1());
        boletim.setNota2(dto.getNota2());
        boletim.setNota3(dto.getNota3());
        boletim.setNota4(dto.getNota4());
        boletim.setRec1(dto.getRec1());
        boletim.setRec2(dto.getRec2());
        boletim.setRec3(dto.getRec3());
        boletim.setRec4(dto.getRec4());
        boletim.setRecFinal(dto.getRecFinal());

        return boletim;
    }
}