package ru.gonch.spring.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.repository.CommentRepository;

@Component
public class DeleteBookMongoEventListener extends AbstractMongoEventListener<Book> {
    private final CommentRepository commentRepository;

    public DeleteBookMongoEventListener(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        commentRepository.getCommentsByBookId(event.getSource().get("_id").toString()).forEach(commentRepository::delete);
    }
}
