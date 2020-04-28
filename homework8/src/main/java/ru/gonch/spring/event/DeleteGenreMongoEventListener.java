package ru.gonch.spring.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.repository.BookRepository;

@Component
public class DeleteGenreMongoEventListener extends AbstractMongoEventListener<Genre> {
    private final BookRepository bookRepository;

    public DeleteGenreMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event) {
        super.onAfterDelete(event);
        bookRepository.getBooksByGenreId(event.getSource().get("_id").toString()).forEach(bookRepository::delete);
    }
}
