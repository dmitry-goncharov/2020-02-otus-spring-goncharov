package ru.gonch.spring.service;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;
    private final Locale locale;

    public MessageServiceImpl(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}
