package ru.gonch.spring.game;

import ru.gonch.spring.domain.Card;
import ru.gonch.spring.service.CardService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CardGameCli {
    private static final String CHARSET_NAME = "UTF-8";

    private static final String GAME_NAME = "Игра \"Угадай столицу\"";
    private static final String NAME_OR_QUIT = "Введите ваше имя или q для выхода";
    private static final String BYE = "До скорой встречи";
    private static final String GAME_DESCRIPTION = "%s, вам будет задано %d вопросов с подсказками";
    private static final String QUESTION_NUMBER = "Вопрос #%d";
    private static final String QUESTION = "%s?";
    private static final String HINTS = "Подсказки:";
    private static final String HINT = "%s - %s";
    private static final String HINT_USAGE = "Выберите правильный ответ и введите соответствующую цифру подсказки";
    private static final String CORRECT = "Верно";
    private static final String INCORRECT = "Неверно";
    private static final String SUM_UP = "Вы ответили правильно на %d из %d";

    private final CardService cardService;

    public CardGameCli(CardService cardService) {
        this.cardService = cardService;
    }

    public void play() {
        List<Card> cards = cardService.getCards();

        Scanner in = new Scanner(System.in, CHARSET_NAME);

        System.out.println(GAME_NAME);
        System.out.println(NAME_OR_QUIT);

        while (in.hasNextLine()) {
            String name = in.nextLine();

            if ("q".equalsIgnoreCase(name)) {
                System.out.println(BYE);
                break;
            }

            if (name.isEmpty()) {
                continue;
            }

            System.out.println(String.format(GAME_DESCRIPTION, name, cards.size()));
            int correctAnswers = 0;
            for (int i = 0; i < cards.size(); i++) {
                System.out.println(String.format(QUESTION_NUMBER, i + 1));
                System.out.println(String.format(QUESTION, cards.get(i).getQuestion()));

                Map<String, String> varsMap = getVarsMap(cards.get(i).getVariants());
                System.out.println(HINTS);
                varsMap.forEach((num, var) -> System.out.println(String.format(HINT, num, var)));
                System.out.println(HINT_USAGE);

                String answer = in.nextLine();
                if (cards.get(i).getAnswer().equalsIgnoreCase(varsMap.get(answer))) {
                    System.out.println(CORRECT);
                    correctAnswers++;
                } else {
                    System.out.println(INCORRECT);
                }
            }
            System.out.println(String.format(SUM_UP, correctAnswers, cards.size()));
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
