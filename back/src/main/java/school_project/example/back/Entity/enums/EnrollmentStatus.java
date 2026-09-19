package school_project.example.back.Entity.enums;

// Situação atual da matrícula do aluno na turma.
public enum EnrollmentStatus {
    // Matrícula em andamento ou ativa.
    ACTIVE,

    // Aluno aprovado no período/turma.
    APPROVED,

    // Aluno reprovado.
    FAILED,

    // Aluno transferido para outra instituição ou turma.
    TRANSFERRED,

    // Matrícula cancelada.
    CANCELLED
}
