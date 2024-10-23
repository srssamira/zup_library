package com.library.zup_library.services.books;

import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.controllers.dtos.books.BookUpdateDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.AuthorRepository;
import com.library.zup_library.repositories.BookRepository;
import com.library.zup_library.services.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Book saveBook(BookRegisterDTO bookRegisterDTO) {
        List<Author> authors = bookRegisterDTO.getAuthorsId().stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("author not found (ID " + authorId + ")")))
                .collect(Collectors.toList());

        return bookRepository.save(BookMapper.toBook(bookRegisterDTO, authors));
    }

    public BookResponseDTO findBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("book not found (ID " + bookId + ")"));
        return BookMapper.toBookResponseDTO(book);
    }

    private Book searchBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.orElseThrow(() -> new RuntimeException("book not found (ID " + bookId + ")"));
    }

    public Book updateBook(BookUpdateDTO bookToUpdate, Long id) {

        if (bookRepository.existsById(id)) {
            Book book = searchBook(bookToUpdate.getId());

            if (!book.getTitle().equals(bookToUpdate.getTitle())) {
                book.setTitle(bookToUpdate.getTitle());
            }

            if (!book.getDescription().equals(bookToUpdate.getDescription())) {
                book.setDescription(bookToUpdate.getDescription());
            }

            List<Long> authorsId = book.getAuthors().stream().map(author -> author.getId()).collect(Collectors.toList());
            List<Long> authorsToUpdate = bookToUpdate.getAuthors();

            if (authorsId.size() != authorsToUpdate.size()) {
                List<Author> updatedAuthors = authorsToUpdate.stream()
                        .map(authorId -> authorRepository.findById(authorId)
                                .orElseThrow(() -> new RuntimeException("author not found")))
                        .collect(Collectors.toList());
                book.setAuthors(updatedAuthors);

            } else {
                for (int index = 0; index < authorsId.size(); index++) {
                    Author author = authorRepository.findById(authorsId.get(index)).get();
                    Author authorUpdated = authorRepository.findById(authorsToUpdate.get(index)).get();

                    if (!author.getId().equals(authorUpdated.getId())) {
                        authorsId.set(index, authorUpdated.getId());
                    }
                }
            }

            return bookRepository.save(book);
        } else throw new RuntimeException("book not found");
    }


}
