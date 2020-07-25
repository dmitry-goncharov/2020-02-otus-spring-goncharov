package ru.gonch.spring.actuate;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DetailInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("local-date-time", LocalDateTime.now());
    }
}
