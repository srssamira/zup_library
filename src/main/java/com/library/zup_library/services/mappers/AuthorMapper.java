package com.library.zup_library.services.mappers;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;

import java.util.List;

public class AuthorMapper {

    public static Author toAuthor (AuthorRegisterDTO authorRegisterDTO, List<Book> books) {
        Author author = new Author();
        author.setName(authorRegisterDTO.getName());
        author.setLastName(authorRegisterDTO.getLastName());
        author.setYearOfBirth(authorRegisterDTO.getYearOfBirth());
        author.setYearOfDeath(authorRegisterDTO.getYearOfDeath());
        author.setBooks(books);

        for (Book book : books) {
            book.getAuthors().add(author);
        }
        return author;
    }


}
