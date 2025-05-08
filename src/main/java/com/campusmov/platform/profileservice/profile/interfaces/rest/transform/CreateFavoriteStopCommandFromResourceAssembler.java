package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateFavoriteStopCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateFavoriteStopResource;

public class CreateFavoriteStopCommandFromResourceAssembler {
    public static CreateFavoriteStopCommand toCommand(CreateFavoriteStopResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("FavoriteStopResource cannot be null");
        }
        return new CreateFavoriteStopCommand(
                resource.name(),
                resource.description(),
                resource.locationName(),
                resource.locationLatitude(),
                resource.locationLongitude(),
                resource.address()
        );
    }
}