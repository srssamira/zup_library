package com.library.zup_library.controllers.dtos.authors;

import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorResponseDTO {
    private String name;
    private String lastName;
    private int yearOfBirth;
    private int yearOfDeath;
    private List<BookResponseDTO> books;
}
