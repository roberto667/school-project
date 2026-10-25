# Guia do Backend

## Tecnologias

- Java 21.
- Spring Boot 4.0.6.
- Spring MVC, Spring Security, Spring Data JPA e Bean Validation.
- PostgreSQL em execução normal.
- JWT com JJWT 0.12.6.
- H2 em memória nos testes.

O manifesto de dependências e plugins é `back/pom.xml`.

## Executar localmente

Configure as variáveis no terminal antes de iniciar a aplicação. Não coloque credenciais reais no Git nem neste vault.

| Variável | Uso |
|---|---|
| `DB_URL` | URL JDBC do PostgreSQL, por exemplo `jdbc:postgresql://localhost:5432/school_project` |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo forte usado para assinar tokens |
| `JWT_EXPIRATION` | Validade do token em milissegundos; padrão `86400000` |
| `CORS_ALLOWED_ORIGINS` | Origens permitidas separadas por vírgula; padrão `http://localhost:5173` |

Exemplo PowerShell, com valores apenas ilustrativos:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/school_project"
$env:DB_USERNAME = "seu_usuario"
$env:DB_PASSWORD = "sua_senha"
$env:JWT_SECRET = "substitua-por-um-segredo-forte"
$env:CORS_ALLOWED_ORIGINS = "http://localhost:5173"
.\mvnw.cmd spring-boot:run
```

As propriedades correspondentes ficam em `back/src/main/resources/application.properties`. O Hibernate está configurado com `ddl-auto=update`; para ambientes compartilhados ou produção, alterações de schema devem ser controladas e revisadas, não tratadas como substituto de migrações.

### Testes

```powershell
.\mvnw.cmd test
```

O perfil `test` usa H2 em memória em `back/src/test/resources/application-test.properties`; ele valida a inicialização do contexto sem depender de um PostgreSQL local. Isso não substitui testes de integração com PostgreSQL.

## Organização em camadas

1. **Controller:** mapeia HTTP, valida DTOs com `@Valid` e retorna códigos/status.
2. **DTO:** define o JSON aceito ou devolvido, sem expor diretamente a entidade.
3. **Service:** aplica validações e regras de negócio.
4. **Repository:** consulta e persiste entidades com Spring Data JPA.
5. **Entity:** representa o modelo persistido.
6. **Mapper:** converte entre DTOs e entidades.
7. **Exception:** contém erros de domínio e o tratamento HTTP global.
8. **Security:** valida JWT, autentica contas e autoriza por papel.

Fluxo resumido:

```text
HTTP → Controller → DTO/validação → Service → Repository → Entity/DB
                    ← Mapper/Response DTO ←
```

## Autenticação e autorização

- `POST /api/auth/login` é público.
- Após autenticação, a resposta contém `token`, `user` e `role`.
- Chamadas protegidas devem enviar `Authorization: Bearer <token>`.
- O backend usa sessões stateless e valida o JWT pelo filtro de segurança.
- Leituras de dados escolares (GET) são permitidas aos papéis indicados abaixo; operações de escrita seguem regras específicas por módulo.
- CORS aceita a origem definida por `CORS_ALLOWED_ORIGINS`. Em produção, defina explicitamente os domínios do frontend.
- A verificação de rota no React melhora a experiência, mas não substitui a autorização no backend.

### Matriz de permissões implementada

| Recurso | GET | Escrita |
|---|---|---|
| `/api/auth/**` | — | Público para autenticação |
| `/api/users/**`, `/api/directors/**` | Diretor | Diretor |
| `/api/students/**`, `/api/teachers/**` | Aluno, professor, diretor | Diretor |
| `/api/subjects/**`, `/api/school-grades/**`, `/api/classrooms/**` | Aluno, professor, diretor | Diretor |
| `/api/enrollments/**`, `/api/teaching-assignments/**`, `/api/academic-periods/**` | Aluno, professor, diretor | Diretor |
| `/api/grades/**`, `/api/attendances/**` | Aluno, professor, diretor | Professor |
| Outras rotas | Usuário autenticado | Usuário autenticado, salvo regra mais específica |

Confira `SecurityConfig.java` antes de depender dessa matriz: alterações de segurança devem ser feitas no backend e refletidas nesta documentação.

## API REST

Todos os recursos abaixo usam o prefixo `/api`. Salvo login, é necessário token. Campos de entrada estão descritos nos DTOs correspondentes.

### Login

| Método | Rota | Uso |
|---|---|---|
| POST | `/auth/login` | Recebe `email` e `password`; retorna token, usuário e papel |

### Usuários e perfis

| Base | Rotas disponíveis |
|---|---|
| `/users` | `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}` |
| `/directors` | `POST /`, `GET /`, `GET /{id}`, `GET /matricula/{matricula}`, `PUT /{id}`, `PUT /matricula/{matricula}`, `DELETE /{id}`, `DELETE /matricula/{matricula}` |
| `/students` | `POST /`, `GET /`, `GET /{id}`, `GET /matricula/{matricula}`, `PUT /{id}`, `PUT /matricula/{matricula}`, `PATCH /{id}/deactivate`, `PATCH /matricula/{matricula}/deactivate`, `PATCH /matricula/{matricula}/activate` |
| `/teachers` | `POST /`, `GET /`, `GET /{id}`, `GET /matricula/{matricula}`, `PUT /{id}`, `PUT /matricula/{matricula}`, `PATCH /{id}/deactivate`, `PATCH /matricula/{matricula}/deactivate`, `PATCH /matricula/{matricula}/activate` |

Perfis de aluno e professor são criados com um objeto `user` aninhado, além da matrícula do perfil.

### Organização acadêmica

| Base | Rotas disponíveis |
|---|---|
| `/subjects` | `POST /`, `GET /`, `GET /{id}`, `GET /name/{nome}`, `PUT /{id}`, `DELETE /{id}` |
| `/school-grades` | `POST /`, `GET /`, `GET /{id}`, `GET /search?grau=...&ano=...`, `PUT /{id}`, `DELETE /{id}` |
| `/classrooms` | `POST /`, `GET /`, `GET /{id}`, `GET /year/{academicYear}`, `GET /school-grade/{schoolGradeId}`, `GET /search?schoolGradeId=...&academicYear=...`, `PUT /{id}`, `DELETE /{id}` |
| `/academic-periods` | `POST /`, `GET /`, `GET /{id}`, `GET /number/{number}`, `PUT /{id}`, `DELETE /{id}` |

### Matrículas e atribuições

| Base | Rotas disponíveis |
|---|---|
| `/enrollments` | `POST /`, `GET /`, `GET /{id}`, `GET /student/{studentId}`, `GET /classroom/{classroomId}`, `GET /student/{studentId}/status/{status}`, `PUT /{id}/status`, `DELETE /{id}` |
| `/teaching-assignments` | `POST /`, `GET /`, `GET /{id}`, `GET /teacher/{teacherId}`, `GET /classroom/{classroomId}`, `GET /subject/{subjectId}`, `DELETE /{id}` |

### Notas e presença

| Base | Rotas disponíveis |
|---|---|
| `/grades` | `POST /`, `GET /`, `GET /{id}`, `GET /enrollment/{enrollmentId}`, `GET /assignment/{assignmentId}`, `GET /enrollment/{enrollmentId}/assignment/{assignmentId}`, `PUT /{id}`, `DELETE /{id}` |
| `/attendances` | `POST /`, `GET /`, `GET /{id}`, `GET /enrollment/{enrollmentId}`, `GET /assignment/{assignmentId}`, `GET /enrollment/{enrollmentId}/assignment/{assignmentId}`, `GET /enrollment/{enrollmentId}/count`, `GET /enrollment/{enrollmentId}/assignment/{assignmentId}/count`, `PUT /{id}`, `DELETE /{id}` |

Para os detalhes exatos dos parâmetros, DTOs e status, consulte os controllers em `back/src/main/java/school_project/example/back/Controller/` e os DTOs correspondentes.

## Exemplos de payload

Login:

```json
{
  "email": "ana.aluna@example.test",
  "password": "senha-fornecida-no-ambiente"
}
```

Criação de turma:

```json
{
  "name": "7A",
  "schoolGradeId": 7,
  "academicYear": 2026
}
```

Lançamento de nota:

```json
{
  "enrollmentId": 1,
  "teachingAssignmentId": 2,
  "periodId": 1,
  "value": 8.5,
  "recoveryValue": null
}
```

Esses IDs são ilustrativos. Consulte o banco local e as validações dos DTOs antes de usar exemplos em uma integração real.
