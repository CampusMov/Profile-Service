package com.campusmov.platform.profileservice.profile.application.internal.outboundservices;

import com.campusmov.platform.profileservice.profile.domain.model.events.ProfileCreatedEvent;
import com.campusmov.platform.profileservice.profile.infrastructure.brokers.kafka.ProfileEventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ProfileEventPublisherService {
    private final ProfileEventSource profileEventSource;

    @TransactionalEventListener
    public void handleProfileCreatedEvent(ProfileCreatedEvent event) {
        profileEventSource.publishEvent(event);
    }
}
