# Grau escolar

`SchoolGrade` representa a etapa de ensino e o ano dentro dessa etapa. O valor `ano` não é o ano civil/letivo.

| id | grau | ano | Interpretação |
|---:|---|---:|---|
| 1 | FUNDAMENTAL_1 | 1 | 1º ano do Fundamental I |
| 2 | FUNDAMENTAL_1 | 2 | 2º ano do Fundamental I |
| 3 | FUNDAMENTAL_2 | 1 | 6º ano escolar (conforme convenção do exemplo) |
| 4 | FUNDAMENTAL_2 | 2 | 7º ano escolar (conforme convenção do exemplo) |
| 5 | FUNDAMENTAL_2 | 3 | 8º ano escolar (conforme convenção do exemplo) |

Valores aceitos para `grau`: `FUNDAMENTAL_1`, `FUNDAMENTAL_2`, `MEDIO` e `GRADUACAO`. A interpretação numérica é convenção do domínio; o backend armazena `grau` e `ano`.
