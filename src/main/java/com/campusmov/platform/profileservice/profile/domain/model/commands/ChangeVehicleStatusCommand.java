package com.campusmov.platform.profileservice.profile.domain.model.commands;

public record ChangeVehicleStatusCommand(
        String vehicleStatus
) {
    public ChangeVehicleStatusCommand {
        if (vehicleStatus == null || vehicleStatus.isBlank()) {
            throw new IllegalArgumentException("Vehicle status cannot be null or empty");
        }
    }
}
