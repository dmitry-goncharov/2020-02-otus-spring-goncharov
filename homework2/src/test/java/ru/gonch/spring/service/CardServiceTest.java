package ru.gonch.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gonch.spring.dao.CardDao;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardDao cardDao;

    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = new CardServiceImpl(cardDao);
    }

    @Test
    void getCards() {
        given(cardDao.getCards()).willReturn(new ArrayList<>());

        assertNotNull(cardService.getCards());
    }
}
