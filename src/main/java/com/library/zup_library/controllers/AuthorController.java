package com.library.zup_library.controllers;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorUpdateDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.services.authors.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody @Valid AuthorRegisterDTO authorRegisterDTO) {
        return authorService.saveAuthor(authorRegisterDTO);
    }

    @GetMapping("{authorId}")
    public AuthorResponseDTO getAuthorById(@PathVariable Long authorId) {
        return authorService.findAuthorById(authorId);
    }

    @PutMapping("/{authorId}")
    public Author updateAuthor(@PathVariable Long authorId, @RequestBody @Valid AuthorUpdateDTO authorUpdateDTO) {
        return authorService.updateAuthor(authorUpdateDTO, authorId);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
