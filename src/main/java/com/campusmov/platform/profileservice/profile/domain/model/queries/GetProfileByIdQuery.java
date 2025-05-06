package com.campusmov.platform.profileservice.profile.domain.model.queries;

public record GetProfileByIdQuery(Long profileId) {
    public GetProfileByIdQuery {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("profileId cannot be null or less than or equal to 0");
        }
    }
}
