package com.library.zup_library.services.authors;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorUpdateDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.AuthorRepository;
import com.library.zup_library.repositories.BookRepository;
import com.library.zup_library.services.mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Author saveAuthor(AuthorRegisterDTO authorRegisterDTO) {

        List<Book> books = Optional.ofNullable(authorRegisterDTO.getBooksId()).orElse(Collections.emptyList())
                .stream().map(booksId -> bookRepository.findById(booksId).get())
                .collect(Collectors.toList());
        return authorRepository.save(AuthorMapper.toAuthor(authorRegisterDTO, books));
    }

    public AuthorResponseDTO findAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("book not found (ID " + authorId + ")"));
        return AuthorMapper.toAuthorResponseDTO(author);
    }

    private Author searchAuthor(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.orElseThrow(() -> new RuntimeException("book not found (ID " + authorId + ")"));
    }

    public Author updateAuthor(AuthorUpdateDTO authorToUpdate, Long id) {

        if (authorRepository.existsById(id)) {
            Author author = searchAuthor(authorToUpdate.getId());

            if (!author.getName().equals(authorToUpdate.getName())) {
                author.setName(authorToUpdate.getName());
            }

            if (!author.getLastName().equals(authorToUpdate.getLastName())) {
                author.setLastName(authorToUpdate.getLastName());
            }

            if (author.getYearOfBirth() != authorToUpdate.getYearOfBirth()) {
                author.setYearOfBirth(authorToUpdate.getYearOfBirth());
            }

            if (author.getYearOfDeath() != authorToUpdate.getYearOfDeath()) {
                author.setYearOfDeath(authorToUpdate.getYearOfDeath());
            }

            List<Long> booksId = author.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList());
            List<Long> booksIdUpdate = authorToUpdate.getBooks();

            if (booksId.size() != booksIdUpdate.size()) {
                List<Book> books = booksIdUpdate.stream()
                        .map(bookId -> bookRepository.findById(bookId)
                                .orElseThrow(() -> new RuntimeException("book not found")))
                        .collect(Collectors.toList());
                author.setBooks(books);

            } else {
                for (int index = 0; index < booksId.size(); index++) {
                    Book book = bookRepository.findById(booksId.get(index)).get();
                    Book bookToUpdate = bookRepository.findById(booksIdUpdate.get(index)).get();

                    if (!book.getId().equals(bookToUpdate.getId())) {
                        booksId.set(index, bookToUpdate.getId());
                    }
                }
            }

            return authorRepository.save(author);
        } else throw new RuntimeException("author not found");
    }

    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    };
}
