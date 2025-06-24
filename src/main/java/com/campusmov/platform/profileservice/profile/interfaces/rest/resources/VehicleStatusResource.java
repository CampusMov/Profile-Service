package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import lombok.Builder;

@Builder
public record VehicleStatusResource(
        String vehicleStatus
) {
}
