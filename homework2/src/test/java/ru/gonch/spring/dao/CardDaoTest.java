package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDaoTest {
    private CardDao cardDao;

    @Test
    void getCards() {
        cardDao = new CardDaoResCsv("/qa.csv");

        assertEquals(1, cardDao.getCards().size());
        assertEquals(4, cardDao.getCards().get(0).getVariants().size());
    }

    @Test
    void getCardsFailed() {
        cardDao = new CardDaoResCsv("/qaf.csv");

        assertEquals(0, cardDao.getCards().size());
    }
}
