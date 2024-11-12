package com.library.zup_library.controllers.dtos.authenticators;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String nickname;
    private String password;
    private String name;
    private String email;
}
