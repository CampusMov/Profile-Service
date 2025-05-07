package com.campusmov.platform.profileservice.profile.domain.model.queries;

public record GetClassSchedulesByProfileIdQuery(String profileId) {
    public GetClassSchedulesByProfileIdQuery {
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("profileId cannot be null or empty");
        }
    }
}
