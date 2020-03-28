package ru.gonch.spring.game;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.domain.Card;
import ru.gonch.spring.service.CardService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.gonch.spring.game.CardGameShell.State.AGAIN;
import static ru.gonch.spring.game.CardGameShell.State.GREETING;
import static ru.gonch.spring.game.CardGameShell.State.QUIZ;

@ShellComponent
public class CardGameShell {
    private static final String GAME_NAME = "The game \"Guess the capital\"";
    private static final String NAME_OR_QUIT = "Enter \"name\" and your name or \"quit\" to exit";
    private static final String GAME_DESCRIPTION = "%s, you will be asked %s hint questions";
    private static final String QUESTION_NUMBER = "Question #%d";
    private static final String HINTS = "Hints:";
    private static final String HINT = "%s - %s";
    private static final String HINT_USAGE = "Choose the correct answer and enter \"answer\" and the appropriate hint number or \"quit\" to exit";
    private static final String CORRECT = "Correct";
    private static final String INCORRECT = "Incorrect";
    private static final String SUM_UP = "You answered correctly to %d from %d";
    private static final String AGAIN_OR_QUIT = "Do you want to try one more time? Press \"again\" to play again or \"quit\" to exit";

    private final CardService cardService;

    private State state = GREETING;
    private int cardIndex;
    private int correctAnswers;

    public CardGameShell(CardService cardService) {
        this.cardService = cardService;
        System.out.println(GAME_NAME);
        System.out.println(NAME_OR_QUIT);
    }

    @ShellMethod(value = "Name command", key = {"name"})
    public String nameCommand(@ShellOption(defaultValue = "Anonymous") String name) {
        List<Card> cards = cardService.getCards();

        state = QUIZ;

        return getLinedString(
                String.format(GAME_DESCRIPTION, name, cards.size()),
                String.format(QUESTION_NUMBER, cardIndex + 1),
                cards.get(cardIndex).getQuestion(),
                getHints(cards.get(cardIndex))
        );
    }

    @ShellMethod(value = "Answer command", key = {"answer"})
    @ShellMethodAvailability(value = "isAnswerCommandAvailable")
    public String answerCommand(@ShellOption String answer) {
        List<Card> cards = cardService.getCards();
        Card card = cards.get(cardIndex);
        Map<String, String> varsMap = getVarsMap(card.getVariants());

        String result;
        if (card.getAnswer().equalsIgnoreCase(varsMap.get(answer))) {
            correctAnswers++;
            result = CORRECT;
        } else {
            result = INCORRECT;
        }

        if (cards.size() > cardIndex + 1) {
            state = QUIZ;
            cardIndex++;

            return getLinedString(
                    result,
                    String.format(QUESTION_NUMBER, cardIndex + 1),
                    cards.get(cardIndex).getQuestion(),
                    getHints(cards.get(cardIndex))
            );
        } else {
            String linedString = getLinedString(
                    result,
                    String.format(SUM_UP, correctAnswers, cards.size()),
                    AGAIN_OR_QUIT
            );

            state = AGAIN;
            cardIndex = 0;
            correctAnswers = 0;

            return linedString;
        }
    }

    @ShellMethod(value = "Again command", key = {"again"})
    @ShellMethodAvailability(value = "isAgainCommandAvailable")
    public String againCommand() {
        List<Card> cards = cardService.getCards();

        state = QUIZ;

        return getLinedString(
                String.format(QUESTION_NUMBER, cardIndex + 1),
                cards.get(cardIndex).getQuestion(),
                getHints(cards.get(cardIndex))
        );
    }

    private Availability isAnswerCommandAvailable() {
        return state == QUIZ ? Availability.available() : Availability.unavailable(NAME_OR_QUIT);
    }

    private Availability isAgainCommandAvailable() {
        return state == AGAIN ? Availability.available() : Availability.unavailable(AGAIN_OR_QUIT);
    }

    private String getHints(Card card) {
        StringBuilder sb = new StringBuilder(HINTS);
        sb.append("\n");
        Map<String, String> varsMap = getVarsMap(card.getVariants());
        varsMap.forEach((num, var) -> sb.append(String.format(HINT, num, var)).append("\n"));
        sb.append(HINT_USAGE);
        return sb.toString();
    }

    private static Map<String, String> getVarsMap(List<String> vars) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < vars.size(); i++) {
            map.put(String.valueOf(i + 1), vars.get(i));
        }
        return map;
    }

    private static String getLinedString(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    enum State {
        GREETING,
        QUIZ,
        AGAIN
    }
}
