package ru.gonch.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
