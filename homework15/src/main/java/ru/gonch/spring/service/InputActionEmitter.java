package ru.gonch.spring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gonch.spring.integration.ActionGateway;
import ru.gonch.spring.model.ActionType;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.InputAction;
import ru.gonch.spring.model.User;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.UserRepository;

import java.util.List;
import java.util.Random;

@Service
public class InputActionEmitter {
    private final ActionGateway actionGateway;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public InputActionEmitter(ActionGateway actionGateway,
                              UserRepository userRepository,
                              BookRepository bookRepository) {
        this.actionGateway = actionGateway;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Scheduled(initialDelay = 100, fixedRate = 100)
    public void emitAction() {
        Random random = new Random();
        List<User> users = userRepository.findAll();
        List<Book> books = bookRepository.findAll();
        ActionType[] types = ActionType.values();

        User user = users.get(random.nextInt(users.size()));
        Book book = books.get(random.nextInt(books.size()));
        ActionType type = types[random.nextInt(types.length)];

        InputAction inputAction = new InputAction();
        inputAction.setTimestamp(System.currentTimeMillis());
        inputAction.setType(type);
        inputAction.setUserId(user.getId());
        inputAction.setBookId(book.getId());

        actionGateway.process(inputAction);
    }
}
