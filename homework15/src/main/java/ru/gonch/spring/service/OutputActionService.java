package ru.gonch.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.stereotype.Service;

@Service
public class OutputActionService {
    private static final Logger log = LoggerFactory.getLogger(OutputActionService.class);

    public OutputActionService(@Qualifier("outputChannel") DirectChannel outputChannel) {
        outputChannel.subscribe(x -> log.info(x.getPayload().toString()));
    }
}
