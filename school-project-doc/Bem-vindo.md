# Guia do projeto escolar

Bem-vindo ao vault de documentação do **School Project**. Esta pasta é um vault do Obsidian e usa wikilinks para conectar as notas.

## Comece por aqui

- [[Como funciona o projeto]] — visão geral, módulos e fluxo entre frontend e backend.
- [[Guia do Backend]] — arquitetura Spring, configuração, API e segurança.
- [[Guia do Frontend]] — execução do React, rotas, autenticação e telas existentes.
- [[Comandos Java Spring]] — resumo das entidades e das relações do domínio.
- [[Diagrama do Banco]] — diagrama conceitual derivado das entidades Java.

## Banco de dados e domínio

- [[Exemplos de Dados]] — índice das notas de exemplo.
- [[USERS]]
- [[SCHOOL GRADE]]
- [[CLASSROOM]]
- [[ENROLLMENT]]
- [[SUBJECT]]
- [[TEACHING ASSIGNMENT]]
- [[ACADEMIC PERIOD]]
- [[GRADE]]
- [[ATTENDANCE]]

## Desenvolvimento

- [[Como funciona o processo do Java Spring]] — fluxo de uma requisição no backend.

## Fonte de verdade

Esta documentação descreve o estado atual do código, não funcionalidades planejadas. Para confirmar contratos e regras, consulte principalmente `back/src/main/java/.../Entity`, `DTO`, `Controller`, `Service` e `Security`, além de `front/src`.

Quando código e documentação divergirem, o código executado é a fonte de verdade; atualize esta documentação junto com a mudança.
