package school_project.example.back.Entity.enums;

// Status possível para a presença do aluno em uma aula.
public enum AttendanceStatus {
    // O aluno compareceu normalmente.
    PRESENT,

    // O aluno faltou sem justificativa.
    ABSENT,

    // O aluno faltou, mas a ausência foi justificada.
    JUSTIFIED
}
