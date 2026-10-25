# Como funciona o projeto

## Visão geral

O **School Project** é uma aplicação escolar dividida em dois projetos:

| Parte | Tecnologia | Responsabilidade |
|---|---|---|
| `front/` | React, Vite, React Router | Interface web, navegação, sessão no navegador e chamadas à API |
| `back/` | Java 21, Spring Boot, Spring MVC, Spring Security, Spring Data JPA | API REST, regras de negócio, autorização, persistência e autenticação JWT |
| Banco | PostgreSQL em execução normal | Persistência relacional configurada por variáveis de ambiente |

O frontend chama o backend por HTTP. A URL-base é definida por `VITE_API_URL`; a configuração local atual usa `http://localhost:8080/api`.

## Fluxo principal

1. A pessoa envia e-mail e senha pela tela de login.
2. O frontend envia `POST /api/auth/login`.
3. O backend autentica a conta, identifica o perfil associado e devolve token JWT, dados básicos do usuário e papel (`DIRECTOR`, `TEACHER` ou `STUDENT`).
4. O frontend guarda a resposta em `localStorage` sob a chave `auth` e redireciona para a área correspondente.
5. As chamadas protegidas incluem `Authorization: Bearer <token>`.
6. O filtro JWT valida o token; as regras Spring Security verificam o papel necessário; os serviços consultam ou alteram entidades via repositórios JPA.
7. O frontend apresenta os dados ou uma mensagem de erro.

## Módulos do domínio

- **Identidade e perfis:** usuário, aluno, professor e diretor.
- **Organização escolar:** grau escolar, turma e disciplina.
- **Vínculos acadêmicos:** matrícula de aluno em turma e atribuição de professor/turma/disciplina.
- **Acompanhamento acadêmico:** períodos acadêmicos, notas e presença.

Consulte [[Comandos Java Spring]] e [[Diagrama do Banco]] para as relações. Atualmente `AcademicPeriod` contém nome e número, mas não possui vínculo de ano letivo no modelo Java.

## O que já existe na interface

- Login e proteção de rotas por papel.
- Área do diretor com telas administrativas para alunos, professores, disciplinas, graus escolares e turmas.
- Páginas de aluno e professor ainda são telas iniciais simples com saudação e saída; não apresentam notas, presenças ou matrículas.
- Existem endpoints de backend para notas, presenças, matrículas e atribuições de ensino, mas eles não estão integrados às páginas de aluno/professor no frontend atual.

As telas da interface não substituem a autorização da API. A segurança real é aplicada no backend.

## Estrutura do repositório

```text
back/
  src/main/java/.../Controller/   Endpoints HTTP
  src/main/java/.../DTO/          Contratos de entrada e saída
  src/main/java/.../Entity/       Entidades JPA e enums
  src/main/java/.../Mapper/       Conversão entre entidades e DTOs
  src/main/java/.../Repository/   Persistência e consultas
  src/main/java/.../Security/     JWT, autenticação, autorização e CORS
  src/main/java/.../Service/      Regras de negócio
  src/main/resources/             Configuração da aplicação
  src/test/                       Testes
front/
  src/Services/                   Cliente HTTP e serviços de API
  src/context/                    Estado de autenticação
  src/components/                 Componentes reutilizáveis
  src/pages/                      Telas e áreas por perfil
  .env                            Configuração local do Vite
school-project-doc/               Este vault do Obsidian
```

## Executar e validar

Veja os passos completos em [[Guia do Backend]] e [[Guia do Frontend]]. Em resumo:

- Backend: `cd back` e `.\mvnw.cmd spring-boot:run`.
- Testes backend: `cd back` e `.\mvnw.cmd test`.
- Frontend: `cd front`, `npm install` (primeira vez) e `npm run dev`.
- Verificações frontend: `npm run lint` e `npm run build`.

O backend precisa das variáveis de banco e JWT para execução normal. Os testes usam perfil isolado com H2 em memória.

## Convenções importantes

- Os endpoints usam o prefixo `/api`.
- As propriedades de request/response seguem os DTOs Java; por exemplo, séries usam `grau` e `ano`, enquanto turmas usam `name`, `schoolGradeId` e `academicYear`.
- Senhas não devem ser documentadas com valores reais nem armazenadas em texto puro.
- Os exemplos em [[Exemplos de Dados]] são fictícios e servem para explicar relações, não são dados de acesso.
