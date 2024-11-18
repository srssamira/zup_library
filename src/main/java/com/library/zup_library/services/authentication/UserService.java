package com.library.zup_library.services.authentication;

import com.library.zup_library.controllers.dtos.authenticators.UserRegisterDTO;
import com.library.zup_library.models.User;
import com.library.zup_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByNickname(userRegisterDTO.getNickname())) {
            throw new RuntimeException("nickname is already in use");
        }

        User user = new User();
        user.setNickname(userRegisterDTO.getNickname());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());

        userRepository.save(user);
    }
}
