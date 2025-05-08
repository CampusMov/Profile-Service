package com.campusmov.platform.profileservice.profile.domain.model.commands;

public record DeleteFavoriteStopCommand(
        String favoriteStopId,
        String profileId
) {
    public DeleteFavoriteStopCommand {
        if (favoriteStopId == null || favoriteStopId.isBlank()) {
            throw new IllegalArgumentException("Favorite stop ID cannot be null or blank");
        }
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("Profile ID cannot be null or blank");
        }
    }
}
