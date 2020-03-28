package ru.gonch.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gonch.spring.dao.CardDao;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardDao cardDao;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void getCards() {
        given(cardDao.getCards()).willReturn(new ArrayList<>());

        assertNotNull(cardService.getCards());
        assertEquals(0, cardService.getCards().size());
    }
}
