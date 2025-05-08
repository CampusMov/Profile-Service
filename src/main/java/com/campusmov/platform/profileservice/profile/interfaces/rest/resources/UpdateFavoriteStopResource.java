package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

public record UpdateFavoriteStopResource(
        String name,
        String description,
        String locationName,
        double locationLatitude,
        double locationLongitude,
        String address
) {
}
