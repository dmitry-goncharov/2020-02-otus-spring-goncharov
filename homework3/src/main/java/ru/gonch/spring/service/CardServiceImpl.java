package ru.gonch.spring.service;

import ru.gonch.spring.dao.CardDao;
import ru.gonch.spring.domain.Card;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardDao cardDao;

    public CardServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public List<Card> getCards() {
        return cardDao.getCards();
    }
}
