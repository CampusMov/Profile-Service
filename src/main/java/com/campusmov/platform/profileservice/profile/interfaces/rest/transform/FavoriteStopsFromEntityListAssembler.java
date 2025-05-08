package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.FavoriteStopResource;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteStopsFromEntityListAssembler {

    public static List<FavoriteStopResource> toResources(List<FavoriteStop> favoriteStops) {
        return favoriteStops.stream()
                .map(favoriteStop -> new FavoriteStopResource(
                        favoriteStop.getId(),
                        favoriteStop.getName(),
                        favoriteStop.getDescription(),
                        favoriteStop.getLocation().name(),
                        favoriteStop.getLocation().coordinates().latitude(),
                        favoriteStop.getLocation().coordinates().longitude(),
                        favoriteStop.getLocation().address()
                ))
                .collect(Collectors.toList());
    }
}