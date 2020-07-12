package ru.gonch.spring.service;

import org.junit.jupiter.api.Test;
import ru.gonch.spring.model.OutputAction;
import ru.gonch.spring.model.User;
import ru.gonch.spring.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputActionUserHandlerTest {
    @Test
    void userHandlerTest() {
        User user = new User();
        user.setId(1L);
        user.setName("test-name");

        OutputAction outputAction = new OutputAction();
        outputAction.setUserId(1L);

        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));

        OutputActionUserHandler outputActionUserHandler = new OutputActionUserHandler(userRepositoryMock);

        assertEquals(user.getName(), outputActionUserHandler.handle(outputAction, null).getUserName());
    }
}
