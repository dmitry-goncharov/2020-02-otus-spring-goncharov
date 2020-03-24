package ru.gonch.spring.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gonch.spring.domain.Card;
import ru.gonch.spring.service.CardService;
import ru.gonch.spring.service.MessageStdIOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardGameCliTest {
    @Mock
    private CardService cardService;

    @Mock
    private MessageStdIOService messageStdIOService;

    @InjectMocks
    private CardGameCli cardGameCli;

    @Test
    void greetingAndQuit() {
        given(cardService.getCards()).willReturn(createCards(0));
        given(messageStdIOService.receiveMessage()).willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(1)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void repeatGreetingAndQuit() {
        given(cardService.getCards()).willReturn(createCards(0));
        given(messageStdIOService.receiveMessage()).willReturn("").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(2)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void startQuizAndQuit() {
        given(cardService.getCards()).willReturn(createCards(1));
        given(messageStdIOService.receiveMessage()).willReturn("qwe").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(2)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void startQuizAndHintAndQuit() {
        given(cardService.getCards()).willReturn(createCards(1));
        given(messageStdIOService.receiveMessage()).willReturn("qwe").willReturn("").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(3)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void startQuizAndCorrectAndQuit() {
        given(cardService.getCards()).willReturn(createCards(1));
        given(messageStdIOService.receiveMessage()).willReturn("qwe").willReturn("1").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(3)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.result", 1, 1);
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void startQuizAndIncorrectAndQuit() {
        given(cardService.getCards()).willReturn(createCards(1));
        given(messageStdIOService.receiveMessage()).willReturn("qwe").willReturn("2").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(3)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.result", 0, 1);
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    @Test
    void startQuizAndIncorrectAndRepeatAndCorrectAndQuit() {
        given(cardService.getCards()).willReturn(createCards(1));
        given(messageStdIOService.receiveMessage()).willReturn("qwe").willReturn("2").willReturn("y").willReturn("1").willReturn("q");

        cardGameCli.play();

        verify(messageStdIOService, times(5)).receiveMessage();
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.result", 0, 1);
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.result", 1, 1);
        verify(messageStdIOService, times(1)).sendLocalizedMessage("game.bye");
    }

    private List<Card> createCards(int count) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cards.add(new Card("question", "answer1", Arrays.asList("answer1", "answer2")));
        }
        return cards;
    }
}
