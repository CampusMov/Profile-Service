package com.campusmov.platform.profileservice.profile.domain.model.commands;

public record UpdateFavoriteStopCommand(
        String name,
        String description,
        String locationName,
        double locationLatitude,
        double locationLongitude,
        String address
) {
}
