package ru.gonch.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.gonch.spring.game.CardGameCli;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(CardGameCli.class).play();
        context.close();
    }
}
