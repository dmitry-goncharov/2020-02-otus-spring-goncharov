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
        long id = userRepository.save(newUser).getId();

        assertEquals(newUser.getLogin(), em.find(User.class, id).getLogin());
    }

    @Test
    void getAllTest() {
        List<User> users = userRepository.findAll();

        assertEquals(3, users.size());
        assertEquals("admin", users.get(0).getLogin());
    }

    @Test
    void getByLoginTest() {
        Optional<User> user = userRepository.findByLogin("admin");

        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getLogin());
    }

    @Test
    void updateTest() {
        User user = em.find(User.class, 1L);
        em.detach(user);

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setLogin(user.getLogin());
        newUser.setAuthority("user");
        userRepository.save(newUser);

        assertEquals("user", em.find(User.class, user.getId()).getAuthority());
    }

    @Test
    void deleteByIdTest() {
        userRepository.deleteById(1L);

        assertNull(em.find(User.class, 1L));
    }
}
