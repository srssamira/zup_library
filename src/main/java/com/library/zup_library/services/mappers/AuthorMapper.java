package com.library.zup_library.services.mappers;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorUpdateDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public static AuthorResponseDTO toAuthorResponseDTOWithoutBooks (Author author) {
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setName(author.getName());
        authorResponseDTO.setLastName(author.getLastName());
        authorResponseDTO.setYearOfBirth(author.getYearOfBirth());
        authorResponseDTO.setYearOfDeath(author.getYearOfDeath());
        return authorResponseDTO;
    }

    public static AuthorResponseDTO toAuthorResponseDTO (Author author) {
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setName(author.getName());
        authorResponseDTO.setLastName(author.getLastName());
        authorResponseDTO.setYearOfBirth(author.getYearOfBirth());
        authorResponseDTO.setYearOfDeath(author.getYearOfDeath());

        List<BookResponseDTO> bookResponseDTOList = author.getBooks().stream()
                .map(BookMapper :: toBookResponseDTOWithoutAuthors)
                .collect(Collectors.toList());

        authorResponseDTO.setBooks(bookResponseDTOList);
        return authorResponseDTO;
    }

    public static Author toAuthorByUpdateDTO (AuthorUpdateDTO authorUpdateDTO, BookRepository bookRepository) {
        Author author = new Author();
        author.setId(authorUpdateDTO.getId());
        author.setName(authorUpdateDTO.getName());
        author.setLastName(authorUpdateDTO.getLastName());
        author.setYearOfBirth(authorUpdateDTO.getYearOfBirth());
        author.setYearOfDeath(authorUpdateDTO.getYearOfDeath());

        if (authorUpdateDTO.getBooks() != null
        && !authorUpdateDTO.getBooks().isEmpty()) {
            List<Book> books = authorUpdateDTO.getBooks().stream()
                    .map(bookId -> bookRepository.findById(bookId)
                            .orElseThrow(() -> new RuntimeException("book not found")))
                    .collect(Collectors.toList());
            author.setBooks(books);

            for (Book book : books) {
                List<Author> authors = book.getAuthors();
                if (!authors.contains(author)) {
                    authors.add(author);
                }
            }
        }
        return author;
    }

}
