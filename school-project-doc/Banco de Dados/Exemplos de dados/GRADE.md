# Nota

`Grade` guarda a nota de uma matrícula para uma atribuição de ensino e período acadêmico.

| id | enrollment_id | teaching_assignment_id | period_id | value | recovery_value |
|---:|---:|---:|---:|---:|---:|
| 1 | 1 | 1 | 1 | 8.0 | — |
| 2 | 1 | 1 | 2 | 5.0 | 7.0 |
| 3 | 1 | 2 | 1 | 9.0 | — |

O DTO valida notas entre 0 e 10. `recovery_value` é opcional. As referências apontam para [[ENROLLMENT]], [[TEACHING ASSIGNMENT]] e [[ACADEMIC PERIOD]].
