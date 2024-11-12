package com.library.zup_library.controllers.dtos.authenticators;

import lombok.Data;

@Data
public class LoginDTO {
    private String nickname;
    private String password;
}
