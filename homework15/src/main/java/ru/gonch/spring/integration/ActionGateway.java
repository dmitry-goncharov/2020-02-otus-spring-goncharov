package ru.gonch.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.gonch.spring.model.InputAction;

@MessagingGateway
public interface ActionGateway {
    @Gateway(requestChannel = "inputChanel")
    void process(@Payload InputAction inputAction);
}
