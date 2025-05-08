package com.campusmov.platform.profileservice.profile.domain.model.commands;

public record CreateFavoriteStopCommand(
        String name,
        String description,
        String locationName,
        double locationLatitude,
        double locationLongitude,
        String address
) {}