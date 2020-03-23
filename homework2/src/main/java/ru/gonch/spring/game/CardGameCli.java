package ru.gonch.spring.game;

import ru.gonch.spring.domain.Card;
import ru.gonch.spring.service.CardService;
import ru.gonch.spring.service.MessageService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CardGameCli {
    private static final String CHARSET_NAME = "UTF-8";

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

    private static final String QUIT = "q";

    private final CardService cardService;
    private final MessageService messageService;

    public CardGameCli(CardService cardService, MessageService messageService) {
        this.cardService = cardService;
        this.messageService = messageService;
    }

    public void play() {
        List<Card> cards = cardService.getCards();

        Scanner in = new Scanner(System.in, CHARSET_NAME);

        System.out.println(messageService.getMessage(GAME_NAME));
        System.out.println(messageService.getMessage(NAME_OR_QUIT, QUIT));

        while (in.hasNextLine()) {
            String name = in.nextLine();

            if (QUIT.equalsIgnoreCase(name)) {
                System.out.println(messageService.getMessage(BYE));
                break;
            }

            if (name.isEmpty()) {
                continue;
            }

            System.out.println(messageService.getMessage(GAME_DESCRIPTION, name, cards.size()));
            int correctAnswers = 0;
            for (int i = 0; i < cards.size(); i++) {
                System.out.println(messageService.getMessage(QUESTION_NUMBER, i + 1));
                System.out.println(messageService.getMessage(QUESTION, cards.get(i).getQuestion()));

                Map<String, String> varsMap = getVarsMap(cards.get(i).getVariants());
                System.out.println(messageService.getMessage(HINTS));
                varsMap.forEach((num, var) -> System.out.println(messageService.getMessage(HINT, num, var)));
                System.out.println(messageService.getMessage(HINT_USAGE));

                String answer = in.nextLine();
                if (QUIT.equalsIgnoreCase(answer)) {
                    System.out.println(messageService.getMessage(BYE));
                    break;
                } else {
                    if (cards.get(i).getAnswer().equalsIgnoreCase(varsMap.get(answer))) {
                        System.out.println(messageService.getMessage(CORRECT));
                        correctAnswers++;
                    } else {
                        System.out.println(messageService.getMessage(INCORRECT));
                    }
                }
            }
            System.out.println(messageService.getMessage(SUM_UP, correctAnswers, cards.size()));
            break;
        }
    }

    private static Map<String, String> getVarsMap(List<String> vars) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < vars.size(); i++) {
            map.put(String.valueOf(i + 1), vars.get(i));
        }
        return map;
    }
}
