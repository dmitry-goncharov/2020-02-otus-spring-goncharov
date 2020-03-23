package ru.gonch.spring.service;

public interface MessageService {
    String getMessage(String key, Object... args);
}
