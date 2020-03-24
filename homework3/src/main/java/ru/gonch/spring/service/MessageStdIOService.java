package ru.gonch.spring.service;

public class MessageStdIOService extends StdIOService {
    private final MessageService messageService;

    public MessageStdIOService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendLocalizedMessage(String key, Object... args) {
        sendMessage(messageService.getMessage(key, args));
    }
}
