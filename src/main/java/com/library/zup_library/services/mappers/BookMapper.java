package com.library.zup_library.services.mappers;

import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorUpdateDTO;
import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.controllers.dtos.books.BookUpdateDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public static BookResponseDTO toBookResponseDTOWithoutAuthors(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setDescription(book.getDescription());
        return bookResponseDTO;
    }

    public static BookResponseDTO toBookResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setDescription(book.getDescription());

        List<AuthorResponseDTO> authorResponseDTOList = book.getAuthors().stream()
                .map(AuthorMapper :: toAuthorResponseDTOWithoutBooks)
                .collect(Collectors.toList());

        bookResponseDTO.setAuthors(authorResponseDTOList);
        return bookResponseDTO;
    }

    public static Book toBookByUpdateDTO(BookUpdateDTO bookUpdateDTO, AuthorRepository authorRepository) {
        Book book = new Book();
        book.setId(bookUpdateDTO.getId());
        book.setTitle(bookUpdateDTO.getTitle());
        book.setDescription(bookUpdateDTO.getDescription());

        if (bookUpdateDTO.getAuthors() != null
        && !bookUpdateDTO.getAuthors().isEmpty()) {
            List<Author> authors = bookUpdateDTO.getAuthors().stream()
                    .map(authorId -> authorRepository.findById(authorId)
                            .orElseThrow(() -> new RuntimeException("author not found")))
                    .collect(Collectors.toList());
            book.setAuthors(authors);

            for (Author author : authors) {
                List<Book> books = author.getBooks();
                if (!books.contains(book)) {
                    books.add(book);
                }
            }
        }
        return book;
    }

}
