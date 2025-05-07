package com.campusmov.platform.profileservice.profile.domain.model.queries;

public record GetProfileByIdQuery(String profileId) {
    public GetProfileByIdQuery {
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("profileId cannot be null or empty");
        }
    }
}
