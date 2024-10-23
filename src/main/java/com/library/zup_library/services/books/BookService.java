package com.library.zup_library.services.books;

import com.library.zup_library.controllers.dtos.books.BookRegisterDTO;
import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import com.library.zup_library.models.Book;

public interface BookService {

    Book saveBook(BookRegisterDTO bookRegisterDTO);

    BookResponseDTO findBookById(Long bookId);
}
