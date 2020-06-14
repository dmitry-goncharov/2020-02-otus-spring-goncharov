package ru.gonch.spring.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gonch.spring.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(user -> new User(
                                user.getLogin(),
                                user.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                List.of(new SimpleGrantedAuthority(user.getAuthority()))
                        )
                )
                .orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' not found"));
    }
}
