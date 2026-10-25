# Como funciona o processo do Spring no projeto

Esta nota descreve o caminho de uma requisição na API deste projeto. O guia completo de execução, segurança e rotas está em [[Guia do Backend]].

## Inicialização

1. `BackApplication` inicia a aplicação Spring Boot.
2. Spring lê propriedades e variáveis de ambiente.
3. Configura conexão JPA, serviços, repositórios e filtros de segurança.
4. A aplicação escuta requisições HTTP.

Na execução normal, datasource e segredo JWT vêm de variáveis de ambiente. O perfil `test` usa H2 em memória.

## Caminho de uma requisição

1. Uma rota de `Controller` recebe a requisição.
2. `SecurityConfig` determina se a rota é pública ou qual papel é necessário. Para rotas protegidas, o filtro JWT valida o bearer token.
3. O Spring desserializa JSON em um DTO. `@Valid` aplica validações declaradas no DTO.
4. O controller chama um `Service`, que executa a regra de negócio e pode consultar ou salvar usando um `Repository`.
5. `Mapper` transforma a entidade em DTO de resposta.
6. O controller devolve o status HTTP e o corpo.
7. Exceções de domínio são convertidas em respostas pelo `GlobalExceptionHandler`.

```text
Cliente → Security/JWT → Controller → DTO → Service → Repository → Banco
                                             ↓
Cliente ← HTTP/JSON ← Controller ← Response DTO ← Mapper ← Entity
```

## Onde alterar

| Necessidade | Diretório |
|---|---|
| Nova rota ou status HTTP | `Controller/` |
| Formato JSON de entrada/saída | `DTO/` |
| Regra de negócio | `Service/` e `Service/impl/` |
| Consulta/persistência | `Repository/` |
| Tabela e relações | `Entity/` |
| Conversão de dados | `Mapper/` |
| Permissões e token | `Security/` |
| Resposta de erro | `Exception/` |

Ao alterar um contrato, atualize também [[Guia do Backend]] e as telas/serviços do frontend que o consomem.
