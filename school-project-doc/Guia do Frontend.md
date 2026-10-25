# Guia do Frontend

## Tecnologias

- React 19 e JavaScript (JSX).
- Vite para desenvolvimento e build.
- React Router para rotas.
- Tailwind CSS e estilos locais.
- Framer Motion para animações.
- Lucide React para ícones.

As dependências e scripts estão em `front/package.json`.

## Executar e validar

No PowerShell:

```powershell
Set-Location front
npm install
npm run dev
```

Com o backend local na porta 8080, `front/.env` define:

```dotenv
VITE_API_URL=http://localhost:8080/api
```

`VITE_API_URL` é exposta ao código do navegador pelo Vite; não coloque segredos nela. Para outra API, altere o valor no ambiente local e configure o CORS correspondente no backend. Reinicie o servidor Vite após alterar variáveis de ambiente.

Comandos de verificação:

```powershell
npm run lint
npm run build
npm run preview
```

## Estrutura

| Caminho | Responsabilidade |
|---|---|
| `src/App.jsx` | Rotas da aplicação e composição do provedor de autenticação |
| `src/pages/` | Telas de login, diretor, professor e aluno |
| `src/components/` | Componentes de tela, formulários, tabelas, navegação e UI |
| `src/Services/api.js` | Login, fetch autenticado, cabeçalho JWT e tratamento central de erros HTTP |
| `src/Services/*Service.js` | Operações HTTP específicas de um recurso |
| `src/context/` | Estado de sessão, provedor e hook de autenticação |
| `src/assets/` e `public/` | Recursos estáticos |

## Rotas e papéis

| Rota | Acesso |
|---|---|
| `/login` | Pública |
| `/director` | Papel `DIRECTOR` |
| `/teacher` | Papel `TEACHER` |
| `/student` | Papel `STUDENT` |
| `/403`, `/500`, `/503` | Páginas de erro |
| Outras | Página 404 |

`PrivateRoute` verifica a sessão local e o papel para controlar a navegação. A API deve continuar verificando os papéis independentemente disso.

## Autenticação e chamadas à API

- A resposta de login é guardada em `localStorage` na chave `auth`.
- `authFetch` lê o token e o envia em `Authorization: Bearer <token>`.
- Ao receber 401, o cliente remove a sessão e encaminha para `/login`.
- Respostas 403, 500 e 503 encaminham para as páginas de erro correspondentes.
- As funções de serviço verificam `response.ok`, apresentam a mensagem retornada pelo servidor quando disponível e tratam respostas sem corpo (204).

Não use `authFetch` para login; o endpoint de login é público e usa a função `login`.

## Funcionalidades atuais da interface

- **Login:** autenticação, validação do papel retornado, animação e navegação.
- **Diretor:** listagem, criação e manutenção disponíveis para alunos, professores, matérias, graus escolares e turmas. As ações efetivamente oferecidas por cada componente podem variar; confira a tela antes de assumir que todo endpoint está ligado.
- **Professor e aluno:** atualmente exibem uma saudação com o nome e uma ação de sair.
- **API ainda sem telas correspondentes:** notas, presença, matrículas, períodos acadêmicos e atribuições de ensino possuem controllers no backend, mas não estão integrados às áreas de aluno/professor no frontend atual.

## Convenções ao adicionar uma tela

1. Confirme rota, papel necessário, DTO e status HTTP no backend.
2. Reuse ou estenda `src/Services/api.js` e os serviços por recurso.
3. Trate loading, estado vazio, erro e sucesso sem assumir que a resposta sempre tem JSON.
4. Não confie somente na proteção de rota do frontend para restringir operações.
5. Atualize este guia e [[Guia do Backend]] se mudar a integração ou o contrato.

## Limitações conhecidas

- O frontend é JavaScript/JSX, não TypeScript.
- Não há suíte de testes frontend configurada nos scripts atuais.
- As telas de professor e aluno são placeholders funcionais mínimos, não portais acadêmicos completos.
