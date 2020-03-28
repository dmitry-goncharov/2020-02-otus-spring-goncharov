package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gonch.spring.config.AppConf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CardDaoTest {
    @Mock
    private AppConf appConf;

    @InjectMocks
    private CardDaoResCsv cardDao;

    @Test
    void getCards() {
        given(appConf.getCsvFile()).willReturn("/qa.csv");

        assertEquals(1, cardDao.getCards().size());
        assertEquals(4, cardDao.getCards().get(0).getVariants().size());
    }

    @Test
    void getCardsFailed() {
        given(appConf.getCsvFile()).willReturn("/qaf.csv");

        assertEquals(0, cardDao.getCards().size());
    }
}
