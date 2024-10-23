### Contexto do Exercício

Desenvolvimento de uma aplicação para gerenciar e em uma biblioteca. Cada livro pode ter vários autores e cada autor pode ter escrito vários livros. As operações de CRUD devem permitir criar, ler, atualizar e excluir livros e autores, mantendo a relação. A regra de negócio exige que um livro tenha no máximo 5 autores.

### 1. Configurações Iniciais
   Configurar um projeto com as seguintes dependências:
   ```
   Spring Web
   Spring Data JPA
   PostgreSQL Driver
   Spring Validation
   Lombok (opcional, para reduzir código boilerplate)
   ```

**Resumo:**
   ```
   • Livro e Autor, com uma relação Many To Many.
   • Um livro pode ter no máximo 5 autores.
   • Implementadas para ambos os recursos (livros e autores).
   • Utilizando @Size e @NotBlank com .
   ```
**Entidades:**
```
Livro:
    String title,
    String description,
    List<Autor> autores

Autor:
    String name,
    String lastName,
    int yearOfBirth,
    int yearOfDeath,
    List<Livro> livros
```
