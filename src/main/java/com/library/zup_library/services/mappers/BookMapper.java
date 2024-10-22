package com.library.zup_library.services.mappers;

import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;

import java.util.List;

public class BookMapper {

    public static Book toBook(BookRegisterDTO bookRegisterDTO, List<Author> authors) {
        Book book = new Book();
        book.setTitle(bookRegisterDTO.getTitle());
        book.setDescription(bookRegisterDTO.getDescription());
        book.setAuthors(authors);

        for (Author author : authors) {
            author.getBooks().add(book);
        }
        return book;
    }

    public static BookResponseDTO toBookResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setDescription(book.getDescription());

    }
}
