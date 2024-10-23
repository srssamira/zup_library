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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Book saveBook(BookRegisterDTO bookRegisterDTO) {
        List<Author> authors = findAuthorsByIds(bookRegisterDTO.getAuthorsId());
        return bookRepository.save(BookMapper.toBook(bookRegisterDTO, authors));
    }

    @Override
    public BookResponseDTO findBookById(Long bookId) {
        Book book = searchBook(bookId);
        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public Book updateBook(BookUpdateDTO bookToUpdate, Long id) {
        Book book = searchBook(id);
        updateBookFields(book, bookToUpdate);
        List<Author> updatedAuthors = findAuthorsByIds(bookToUpdate.getAuthors());
        book.setAuthors(updatedAuthors);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("book not found");
        }
        bookRepository.deleteById(bookId);
    }

    private Book searchBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book not found"));
    }

    private List<Author> findAuthorsByIds(List<Long> authorsId) {
        return Optional.ofNullable(authorsId).orElse(List.of())
                .stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("author not found")))
                .collect(Collectors.toList());
    }

    private void updateBookFields(Book book, BookUpdateDTO bookToUpdate) {
        if (!book.getTitle().equals(bookToUpdate.getTitle())) {
            book.setTitle(bookToUpdate.getTitle());
        }
        if (!book.getDescription().equals(bookToUpdate.getDescription())) {
            book.setDescription(bookToUpdate.getDescription());
        }
    }
}
