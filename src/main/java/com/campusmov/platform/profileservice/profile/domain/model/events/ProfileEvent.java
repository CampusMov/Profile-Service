package com.campusmov.platform.profileservice.profile.domain.model.events;

import com.campusmov.platform.profileservice.shared.domain.model.events.DomainEvent;

sealed public interface ProfileEvent extends DomainEvent permits ProfileCreatedEvent {
}
