package ru.gonch.spring.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.repository.BookRepository;

@Component
public class DeleteAuthorMongoEventListener extends AbstractMongoEventListener<Author> {
    private final BookRepository bookRepository;

    public DeleteAuthorMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        bookRepository.getBooksByAuthorId(event.getSource().get("_id").toString()).forEach(bookRepository::delete);
    }
}
