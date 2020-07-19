package ru.gonch.spring.actuate;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.gonch.spring.repository.UserRepository;

import java.util.Map;

@Component
@Endpoint(id = "user")
public class UserInfoEndpoint {
    private final UserRepository userRepository;

    public UserInfoEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ReadOperation
    public Map<String, ?> userInfo() {
        return Map.of(
                "total", userRepository.count(),
                "roles", Map.of(
                        "user", userRepository.countByAuthority("ROLE_USER"),
                        "editor", userRepository.countByAuthority("ROLE_EDITOR"),
                        "admin", userRepository.countByAuthority("ROLE_ADMIN"))
        );
    }
}
