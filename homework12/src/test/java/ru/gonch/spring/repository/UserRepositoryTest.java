package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gonch.spring.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertTest() {
        User newUser = new User();
        newUser.setLogin("login");
        newUser.setPassword("password");
        newUser.setAuthority("authority");
        userRepository.save(newUser);

        assertEquals(newUser.getLogin(), em.find(User.class, "login").getLogin());
    }

    @Test
    void getAllTest() {
        List<User> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("admin", users.get(0).getLogin());
    }

    @Test
    void getByIdTest() {
        Optional<User> user = userRepository.findById("admin");

        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getLogin());
    }

    @Test
    void updateTest() {
        User user = em.find(User.class, "admin");
        em.detach(user);

        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setAuthority("user");
        userRepository.save(newUser);

        assertEquals("user", em.find(User.class, user.getLogin()).getAuthority());
    }

    @Test
    void deleteByIdTest() {
        userRepository.deleteById("admin");

        assertNull(em.find(User.class, "admin"));
    }
}
