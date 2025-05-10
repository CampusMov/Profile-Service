package com.campusmov.platform.profileservice.profile.domain.model.queries;

import java.util.Optional;

public record GetVehicleByOwnerIdOrVehicleIdQuery(
        Optional<String> ownerId,
        Optional<String> vehicleId
) {
}
