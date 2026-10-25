# Frequência

`Attendance` registra a presença de um aluno matriculado em uma aula/atribuição de ensino, em uma data.

| id | enrollment_id | teaching_assignment_id | date | status |
|---:|---:|---:|---|---|
| 1 | 1 | 1 | 2026-09-15 | PRESENT |
| 2 | 1 | 1 | 2026-09-16 | ABSENT |
| 3 | 1 | 2 | 2026-09-17 | JUSTIFIED |

Valores válidos de `status`: `PRESENT`, `ABSENT` e `JUSTIFIED`. A data é `LocalDate` (`YYYY-MM-DD`).

## API

O recurso usa o prefixo `/api/attendances`. Há operações para criar, listar, buscar por ID, matrícula e atribuição, contar registros, atualizar e excluir. O DTO de escrita requer `enrollmentId`, `teachingAssignmentId`, `date` e `status`.

Pelas regras de `SecurityConfig`, GET pode ser acessado por aluno, professor e diretor; operações de escrita são restritas a professor.

As telas atuais de aluno e professor ainda não consomem essas rotas. Consulte [[Guia do Backend]] e [[Guia do Frontend]].
