package com.library.zup_library.controllers;

import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.models.Book;
import com.library.zup_library.services.books.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody @Valid BookRegisterDTO bookRegisterDTO) {
        return bookService.saveBook(bookRegisterDTO);
    }

    @GetMapping("/{bookId}")
    public BookResponseDTO getBookById(@PathVariable long bookId) {
        return bookService.findBookById(bookId);
    }
}
