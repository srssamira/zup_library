package com.library.zup_library.controllers.dtos.authors;

import com.library.zup_library.controllers.dtos.books.BookResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorUpdateDTO {
    @NotNull
    private Long id;

    @NotNull(message = "name can't be empty")
    @NotBlank(message = "name can't be blank")
    private String name;

    @NotNull(message = "last name can't be empty")
    @NotBlank(message = "last name can't be blank")
    private String lastName;

    @NotNull(message = "yearOfBirth can't be empty")
    private int yearOfBirth;

    private int yearOfDeath;
    private List<Long> books;
}
