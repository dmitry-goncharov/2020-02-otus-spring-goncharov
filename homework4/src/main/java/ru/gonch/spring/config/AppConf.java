package ru.gonch.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConf {
    private String csvFile;

    public String getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(String csvFile) {
        this.csvFile = csvFile;
    }
}
