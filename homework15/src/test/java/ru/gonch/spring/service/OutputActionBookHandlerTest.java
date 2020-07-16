package ru.gonch.spring.service;

import org.junit.jupiter.api.Test;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.OutputAction;
import ru.gonch.spring.repository.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputActionBookHandlerTest {
    @Test
    void bookHandlerTest() {
        Book book = new Book();
        book.setId(1L);
        book.setName("test-name");

        OutputAction outputAction = new OutputAction();
        outputAction.setBookId(1L);

        BookRepository bookRepositoryMock = mock(BookRepository.class);
        when(bookRepositoryMock.findById(1L)).thenReturn(Optional.of(book));

        OutputActionBookHandler outputActionBookHandler = new OutputActionBookHandler(bookRepositoryMock);

        assertEquals(book.getName(), outputActionBookHandler.handle(outputAction, null).getBookName());
    }
}
