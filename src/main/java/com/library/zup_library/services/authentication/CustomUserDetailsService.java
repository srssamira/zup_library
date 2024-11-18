package com.library.zup_library.services.authentication;

import com.library.zup_library.models.User;
import com.library.zup_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname).orElseThrow(() ->
                new UsernameNotFoundException("non-existent user, check your login information"));

        return new org.springframework.security.core.userdetails.User(nickname, user.getPassword(), Collections.emptyList());
    }

}
