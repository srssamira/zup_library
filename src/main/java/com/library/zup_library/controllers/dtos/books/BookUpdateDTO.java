package com.library.zup_library.controllers.dtos.books;

import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookUpdateDTO {
    @NotNull
    private Long id;

    @NotNull(message = "title can't be empty")
    @NotBlank(message = "title can't be blank")
    private String title;

    @NotNull(message = "description can't be empty")
    @NotBlank(message = "description can't be blank")
    private String description;

    @Size(max = 5)
    List<Long> authors;
}
