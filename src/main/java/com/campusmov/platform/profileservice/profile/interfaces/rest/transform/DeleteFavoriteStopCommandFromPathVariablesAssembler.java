package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.DeleteFavoriteStopCommand;

public class DeleteFavoriteStopCommandFromPathVariablesAssembler {
    public static DeleteFavoriteStopCommand toCommand(String profileId, String favoriteStopId) {
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("Profile ID cannot be null or blank");
        }
        if (favoriteStopId == null || favoriteStopId.isBlank()) {
            throw new IllegalArgumentException("Favorite Stop ID cannot be null or blank");
        }
        return new DeleteFavoriteStopCommand(profileId, favoriteStopId);
    }
}
