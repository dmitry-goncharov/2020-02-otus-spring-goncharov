package ru.gonch.spring.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CardGameShellTest {
    private static final String GREETING = "Anonymous, you will be asked 1 hint questions\n" +
            "Question #1\n" +
            "What is the capital of Italy?\n" +
            "Hints:\n" +
            "1 - Rome\n" +
            "2 - Milan\n" +
            "3 - Naples\n" +
            "4 - Florence\n" +
            "Choose the correct answer and enter \"answer\" and the appropriate hint number or \"quit\" to exit";
    private static final String CORRECT_ANSWER = "Correct\n" +
            "You answered correctly to 1 from 1\n" +
            "Do you want to try one more time? Press \"again\" to play again or \"quit\" to exit";
    private static final String INCORRECT_ANSWER = "Incorrect\n" +
            "You answered correctly to 0 from 1\n" +
            "Do you want to try one more time? Press \"again\" to play again or \"quit\" to exit";
    private static final String AGAIN = "Question #1\n" +
            "What is the capital of Italy?\n" +
            "Hints:\n" +
            "1 - Rome\n" +
            "2 - Milan\n" +
            "3 - Naples\n" +
            "4 - Florence\n" +
            "Choose the correct answer and enter \"answer\" and the appropriate hint number or \"quit\" to exit";

    @Autowired
    private Shell shell;

    @Test
    void greeting() {
        String res = (String) shell.evaluate(() -> "name");

        assertEquals(GREETING, res);
    }

    @Test
    void correctAnswer() {
        shell.evaluate(() -> "name");
        String res = (String) shell.evaluate(() -> "answer 1");

        assertEquals(CORRECT_ANSWER, res);
    }

    @Test
    void incorrectAnswer() {
        shell.evaluate(() -> "name");
        String res = (String) shell.evaluate(() -> "answer 2");

        assertEquals(INCORRECT_ANSWER, res);
    }

    @Test
    void again() {
        shell.evaluate(() -> "name");
        shell.evaluate(() -> "answer 1");
        String res = (String) shell.evaluate(() -> "again");

        assertEquals(AGAIN, res);
    }
}
