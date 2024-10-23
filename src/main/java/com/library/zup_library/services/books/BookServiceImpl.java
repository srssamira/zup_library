package com.library.zup_library.services.books;

import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.AuthorRepository;
import com.library.zup_library.repositories.BookRepository;
import com.library.zup_library.services.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
