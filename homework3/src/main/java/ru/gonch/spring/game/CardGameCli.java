package ru.gonch.spring.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gonch.spring.domain.Card;
import ru.gonch.spring.service.CardService;
import ru.gonch.spring.service.MessageStdIOService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CardGameCli {
    private static final Logger log = LoggerFactory.getLogger(CardGameCli.class);

    private static final String GAME_NAME = "game.name";
    private static final String NAME_OR_QUIT = "game.login";
    private static final String BYE = "game.bye";
    private static final String GAME_DESCRIPTION = "game.description";
    private static final String QUESTION_NUMBER = "game.question.number";
    private static final String QUESTION = "game.question";
    private static final String HINTS = "game.question.hints";
    private static final String HINT = "game.question.hint";
    private static final String HINT_USAGE = "game.question.hint.usage";
    private static final String CORRECT = "game.question.correct";
    private static final String INCORRECT = "game.question.incorrect";
    private static final String SUM_UP = "game.result";
    private static final String TRY_AGAIN = "game.again";

    private static final String QUIT = "q";
    private static final String YES = "y";

    private final CardService cardService;
    private final MessageStdIOService messageStdIOService;

    public CardGameCli(CardService cardService, MessageStdIOService messageStdIOService) {
        this.cardService = cardService;
        this.messageStdIOService = messageStdIOService;
    }

    public void play() {
        List<Card> cards = cardService.getCards();
        try {
            if (!greetingState(cards.size())) {
                return;
            }
            boolean quiz = true;
            while (quiz) {
                if (!quizState(cards)) {
                    return;
                }
                if (leavingState()) {
                    quiz = false;
                }
            }
        } catch (Exception e) {
            log.error("Error while playing game", e);
        }
    }

    private boolean greetingState(int cards) {
        messageStdIOService.sendLocalizedMessage(GAME_NAME);
        messageStdIOService.sendLocalizedMessage(NAME_OR_QUIT, QUIT);
        while (true) {
            String line = messageStdIOService.receiveMessage();
            if (line != null && line.isEmpty()) {
                messageStdIOService.sendLocalizedMessage(NAME_OR_QUIT, QUIT);
            } else {
                if (line == null || QUIT.equalsIgnoreCase(line)) {
                    messageStdIOService.sendLocalizedMessage(BYE);
                    return false;
                } else {
                    messageStdIOService.sendLocalizedMessage(GAME_DESCRIPTION, line, cards);
                    return true;
                }
            }
        }
    }

    private boolean quizState(List<Card> cards) {
        int correctAnswers = 0;
        for (int i = 0; i < cards.size(); i++) {
            messageStdIOService.sendLocalizedMessage(QUESTION_NUMBER, i + 1);
            messageStdIOService.sendLocalizedMessage(QUESTION, cards.get(i).getQuestion());

            Map<String, String> varsMap = getVarsMap(cards.get(i).getVariants());
            messageStdIOService.sendLocalizedMessage(HINTS);
            varsMap.forEach((num, var) -> messageStdIOService.sendLocalizedMessage(HINT, num, var));
            messageStdIOService.sendLocalizedMessage(HINT_USAGE, QUIT);

            boolean next = false;
            while (true) {
                String line = messageStdIOService.receiveMessage();
                if (line != null && line.isEmpty()) {
                    messageStdIOService.sendLocalizedMessage(HINT_USAGE, QUIT);
                } else {
                    if (line != null &&!QUIT.equalsIgnoreCase(line)) {
                        if (cards.get(i).getAnswer().equalsIgnoreCase(varsMap.get(line))) {
                            messageStdIOService.sendLocalizedMessage(CORRECT);
                            correctAnswers++;
                        } else {
                            messageStdIOService.sendLocalizedMessage(INCORRECT);
                        }
                        next = true;
                    }
                    break;
                }
            }
            if (!next) {
                messageStdIOService.sendLocalizedMessage(BYE);
                return false;
            }
        }
        messageStdIOService.sendLocalizedMessage(SUM_UP, correctAnswers, cards.size());
        return true;
    }

    private boolean leavingState() {
        messageStdIOService.sendLocalizedMessage(TRY_AGAIN, YES);
        while (true) {
            String line = messageStdIOService.receiveMessage();
            if (line != null && line.isEmpty()) {
                messageStdIOService.sendLocalizedMessage(TRY_AGAIN, YES);
            } else {
                if (YES.equalsIgnoreCase(line)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        messageStdIOService.sendLocalizedMessage(BYE);
        return true;
    }

    private static Map<String, String> getVarsMap(List<String> vars) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < vars.size(); i++) {
            map.put(String.valueOf(i + 1), vars.get(i));
        }
        return map;
    }
}
