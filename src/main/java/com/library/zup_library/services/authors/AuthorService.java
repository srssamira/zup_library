package com.library.zup_library.services.authors;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.models.Author;

public interface AuthorService {

    Author saveAuthor(AuthorRegisterDTO authorRegisterDTO);

    AuthorResponseDTO findAuthorById(Long authorId);
}
