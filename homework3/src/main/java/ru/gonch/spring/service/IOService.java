package ru.gonch.spring.service;

public interface IOService<I, O> {
    I receiveMessage();

    void sendMessage(O message);
}
