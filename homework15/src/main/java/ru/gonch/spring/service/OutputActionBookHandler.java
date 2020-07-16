package ru.gonch.spring.service;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.OutputAction;
import ru.gonch.spring.repository.BookRepository;

@Service
public class OutputActionBookHandler implements GenericHandler<OutputAction> {
    private final BookRepository bookRepository;

    public OutputActionBookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public OutputAction handle(OutputAction outputAction, MessageHeaders headers) {
        bookRepository.findById(outputAction.getBookId()).ifPresent(book -> outputAction.setBookName(book.getName()));
        return outputAction;
    }
}
