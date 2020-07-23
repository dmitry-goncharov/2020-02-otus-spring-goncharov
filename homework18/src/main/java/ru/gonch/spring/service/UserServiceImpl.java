package ru.gonch.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.User;
import ru.gonch.spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public boolean deleteById(long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @HystrixCommand(commandKey = "getUserByLoginKey")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
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
