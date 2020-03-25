package ru.gonch.spring.dao;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.gonch.spring.config.AppConf;
import ru.gonch.spring.domain.Card;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CardDaoResCsv implements CardDao {
    private static final Logger log = LoggerFactory.getLogger(CardDaoResCsv.class);

    private static final String CHARSET_NAME = "UTF-8";

    private static final int QUESTION_INDEX = 0;
    private static final int ANSWER_INDEX = 1;
    private static final int MIN_VALID_COLS = 2;

    private final AppConf appConf;

    public CardDaoResCsv(AppConf appConf) {
        this.appConf = appConf;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        try (CSVReader reader = new CSVReader(getReaderFromResources(appConf.getCsvFile()))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                List<String> columns = Arrays.asList(line);
                if (columns.size() >= MIN_VALID_COLS) {
                    cards.add(new Card(
                            columns.get(QUESTION_INDEX),
                            columns.get(ANSWER_INDEX),
                            columns.subList(MIN_VALID_COLS, columns.size())
                    ));
                }
            }
        } catch (Exception e) {
            log.error("Error on getting cards", e);
        }
        return cards;
    }

    private Reader getReaderFromResources(String fileName) {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        if (inputStream != null) {
            try {
                return new InputStreamReader(inputStream, CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("File " + fileName + " is incorrect!");
            }
        } else {
            throw new IllegalArgumentException("File " + fileName + " is not found!");
        }
    }
}
