package ru.gonch.spring.dao;

import ru.gonch.spring.domain.Card;

import java.util.List;

public interface CardDao {
    List<Card> getCards();
}
