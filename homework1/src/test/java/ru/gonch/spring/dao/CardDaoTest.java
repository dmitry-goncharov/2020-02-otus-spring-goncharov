package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDaoTest {
    @Test
    void getCards() {
        CardDao cardDao = new CardDaoResCsv("/qa.csv");

        assertEquals(1, cardDao.getCards().size());
        assertEquals(4, cardDao.getCards().get(0).getVariants().size());
    }

    @Test
    void getCardsFailed() {
        CardDao cardDao = new CardDaoResCsv("/qaf.csv");

        assertEquals(0, cardDao.getCards().size());
    }
}
