# Entitys

Resumo do modelo JPA atual. Para tipos, colunas e regras completas, consulte `back/src/main/java/school_project/example/back/Entity/`.

| Entidade | Tabela | Papel |
|---|---|---|
| `User` | `users` | Conta, e-mail único, senha e estado ativo |
| `Student` | `student` | Perfil de aluno com matrícula única e conta de usuário |
| `Teacher` | `teacher` | Perfil de professor com matrícula única e conta de usuário |
| `Director` | `director` | Perfil de diretor com matrícula e conta |
| `SchoolGrade` | `school_grades` | Grau de ensino e ano da etapa |
| `Classroom` | `classrooms` | Turma, ligada a um grau escolar e a um ano acadêmico |
| `Subject` | `subjects` | Disciplina com nome único |
| `Enrollment` | `enrollments` | Relação de aluno com turma e estado da matrícula |
| `TeachingAssignment` | `teaching_assignments` | Relação de professor, turma e disciplina |
| `AcademicPeriod` | `academic_periods` | Nome e número de período acadêmico |
| `Grade` | `grades` | Nota e recuperação ligadas a matrícula, atribuição e período |
| `Attendance` | `attendance` | Presença ligada a matrícula e atribuição em uma data |

## Relações

- Uma conta `User` pode ter perfil `Student`, `Teacher` ou `Director`; cada perfil guarda referência única para sua conta. O modelo não declara uma restrição global que impeça a mesma conta de estar ligada a mais de um tipo de perfil.
- Vários alunos podem estar matriculados na mesma turma; uma matrícula se refere a um aluno e uma turma.
- Uma atribuição de ensino referencia um professor, uma turma e uma disciplina.
- Notas e presenças relacionam a matrícula do aluno e uma atribuição de ensino. A nota também referencia um período acadêmico.
- Uma turma referencia um `SchoolGrade` e armazena seu próprio `academicYear`.
- `AcademicPeriod` atualmente **não** referencia ano acadêmico; seu modelo tem apenas `name` e `number`.

## Enums

### `Grau`

`FUNDAMENTAL_1`, `FUNDAMENTAL_2`, `MEDIO`, `GRADUACAO`.

### `EnrollmentStatus`

`ACTIVE`, `APPROVED`, `FAILED`, `TRANSFERRED`, `CANCELLED`.

### `AttendanceStatus`

`PRESENT`, `ABSENT`, `JUSTIFIED`.

## Observações de persistência

- Os nomes físicos de tabelas e colunas variam conforme `@Table` e `@JoinColumn`; não deduza o schema somente pelos exemplos visuais antigos.
- `Grade` limita valores de 0 a 10 no DTO de request; `recoveryValue` é opcional.
- O estado `active` fica na conta `User`; desativar um perfil de aluno/professor não é o mesmo que excluir a linha do perfil.
- O Hibernate está configurado com `ddl-auto=update` na configuração padrão. Consulte [[Guia do Backend]] para detalhes de ambiente.

[[Diagrama do Banco]]
