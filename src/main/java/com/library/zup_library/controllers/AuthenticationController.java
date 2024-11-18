package com.library.zup_library.controllers;

import com.library.zup_library.controllers.dtos.authenticators.AuthenticationResponseDTO;
import com.library.zup_library.controllers.dtos.authenticators.LoginDTO;
import com.library.zup_library.services.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        String token = authenticationService.login(loginDTO);

        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setToken(token);

        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }
}
