package ru.gonch.spring.service;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.OutputAction;
import ru.gonch.spring.repository.UserRepository;

@Service
public class OutputActionUserHandler implements GenericHandler<OutputAction> {
    private final UserRepository userRepository;

    public OutputActionUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OutputAction handle(OutputAction outputAction, MessageHeaders headers) {
        userRepository.findById(outputAction.getUserId()).ifPresent(user -> outputAction.setUserName(user.getName()));
        return outputAction;
    }
}
