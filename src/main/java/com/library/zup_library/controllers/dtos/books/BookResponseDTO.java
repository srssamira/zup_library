package com.library.zup_library.controllers.dtos.books;

import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookResponseDTO {
    private String title;
    private String description;
    List<AuthorResponseDTO> authors;
}
