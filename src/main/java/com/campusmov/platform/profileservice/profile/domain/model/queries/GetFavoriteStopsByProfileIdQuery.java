package com.campusmov.platform.profileservice.profile.domain.model.queries;

public record GetFavoriteStopsByProfileIdQuery(String profileId) {
    public GetFavoriteStopsByProfileIdQuery {
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("profileId cannot be null or empty");
        }
    }
}
