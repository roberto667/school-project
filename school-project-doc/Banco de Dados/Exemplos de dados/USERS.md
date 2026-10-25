# Usuários e perfis

Exemplo conceitual com dados totalmente fictícios. Senhas são omitidas deliberadamente.

## `users`

| id | first_name | last_name | email_id |
|---:|---|---|---|
| 1 | Ana | Silva | ana.aluna@example.test |
| 2 | Bruno | Santos | bruno.professor@example.test |
| 3 | Carla | Lima | carla.diretora@example.test |

## `student`

| id | matricula | user_id |
|---:|---|---:|
| 1 | A20260001 | 1 |

## `teacher`

| id | matricula | user_id |
|---:|---|---:|
| 1 | P20260001 | 2 |

## `director`

| id | matricula | user_id |
|---:|---|---:|
| 1 | D20260001 | 3 |

Cada perfil referencia uma conta em `users`. No banco real a senha é armazenada com hash pelo backend; não use senha em texto puro em documentação, amostras compartilhadas ou commits.
