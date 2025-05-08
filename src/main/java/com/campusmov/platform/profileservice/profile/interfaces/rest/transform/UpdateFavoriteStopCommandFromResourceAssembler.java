package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateFavoriteStopCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateFavoriteStopResource;

public class UpdateFavoriteStopCommandFromResourceAssembler {
    public static UpdateFavoriteStopCommand toCommand(UpdateFavoriteStopResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("UpdateFavoriteStopResource cannot be null");
        }
        return new UpdateFavoriteStopCommand(
                resource.name(),
                resource.description(),
                resource.locationName(),
                resource.locationLatitude(),
                resource.locationLongitude(),
                resource.address()
        );
    }
}
