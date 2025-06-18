package com.campusmov.platform.profileservice.profile.infrastructure.brokers.kafka;

import com.campusmov.platform.profileservice.profile.domain.model.events.ProfileEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

@Configuration
public class ProfileEventSource {
    private final Queue<Message<?>> eventQueue = new LinkedList<>();

    @Bean
    public Supplier<Message<?>> profileSupplier() {
        return this.eventQueue::poll;
    }

    public void publishEvent(ProfileEvent event) {
        this.eventQueue.add(MessageBuilder.withPayload(event).build());
    }
}
