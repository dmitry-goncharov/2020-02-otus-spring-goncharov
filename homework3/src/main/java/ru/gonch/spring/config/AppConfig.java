package ru.gonch.spring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.gonch.spring.aop.CacheCardsAspect;
import ru.gonch.spring.dao.CardDao;
import ru.gonch.spring.dao.CardDaoResCsv;
import ru.gonch.spring.game.CardGameCli;
import ru.gonch.spring.service.CardService;
import ru.gonch.spring.service.CardServiceImpl;
import ru.gonch.spring.service.MessageService;
import ru.gonch.spring.service.MessageServiceImpl;
import ru.gonch.spring.service.MessageStdIOService;

import java.util.Locale;

@Configuration
public class AppConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public MessageService messageService(MessageSource messageSource, AppConf appConf) {
        return new MessageServiceImpl(messageSource, Locale.forLanguageTag(appConf.getLanguageTag()));
    }

    @Bean
    public CacheCardsAspect cacheCardsAspect() {
        return new CacheCardsAspect();
    }

    @Bean
    public CardDao cardDao(MessageService messageService) {
        return new CardDaoResCsv(messageService.getMessage("app.csv.file"));
    }

    @Bean
    public CardService cardService(CardDao cardDao) {
        return new CardServiceImpl(cardDao);
    }

    @Bean
    public MessageStdIOService messageStdIOService(MessageService messageService) {
        return new MessageStdIOService(messageService);
    }

    @Bean
    public CardGameCli cardGameCli(CardService cardService, MessageStdIOService messageStdIOService) {
        return new CardGameCli(cardService, messageStdIOService);
    }
}
