package ru.gonch.spring.service;

import ru.gonch.spring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    List<User> getAll();

    Optional<User> getById(long id);

    boolean deleteById(long id);
}
