package ru.gonch.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

@Service
public class ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    public void handleErrorMessage(ErrorMessage errorMessage) {
        log.error("Can't handle message " + errorMessage.getOriginalMessage() + " because of " + errorMessage.getPayload().getMessage());
    }
}
