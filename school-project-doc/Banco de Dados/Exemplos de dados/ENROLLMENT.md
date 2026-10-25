# Matrícula escolar

`Enrollment` liga um aluno a uma turma e registra seu status.

| id | student_id | classroom_id | status |
|---:|---:|---:|---|
| 1 | 1 | 1 | ACTIVE |

O `student_id` referencia o perfil em [[USERS]]; `classroom_id` referencia [[CLASSROOM]]. Status aceitos: `ACTIVE`, `APPROVED`, `FAILED`, `TRANSFERRED` e `CANCELLED`.
