package ru.gonch.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import ru.gonch.spring.service.InputActionFilter;
import ru.gonch.spring.service.InputActionToOutputActionTransformer;
import ru.gonch.spring.service.OutputActionBookHandler;
import ru.gonch.spring.service.OutputActionUserHandler;

@Configuration
public class IntegrationConfig {
    @Bean
    public PollableChannel inputChanel() {
        return MessageChannels.queue("inputChanel", 1000).get();
    }

    @Bean
    public DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(1000).get();
    }

    @Bean
    public IntegrationFlow actionFlow(InputActionFilter inputActionFilter,
                                      InputActionToOutputActionTransformer inputActionToOutputActionTransformer,
                                      OutputActionUserHandler outputActionUserHandler,
                                      OutputActionBookHandler outputActionBookHandler) {
        return IntegrationFlows.from("inputChanel")
                .filter(inputActionFilter)
                .transform(inputActionToOutputActionTransformer)
                .handle(outputActionUserHandler)
                .handle(outputActionBookHandler)
                .channel("outputChannel")
                .get();
    }
}
