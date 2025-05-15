package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.FavoriteStopResource;

public class FavoriteStopResourceFromEntityAssembler {
    public static FavoriteStopResource toResource(FavoriteStop favoriteStop) {
        if (favoriteStop == null) {
            throw new IllegalArgumentException("FavoriteStop cannot be null");
        }
        return new FavoriteStopResource(
                favoriteStop.getId(),
                favoriteStop.getName(),
                favoriteStop.getDescription(),
                favoriteStop.getLocation().name(),
                favoriteStop.getLocation().coordinates().latitude(),
                favoriteStop.getLocation().coordinates().longitude(),
                favoriteStop.getLocation().address()
        );
    }
}