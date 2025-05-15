package com.campusmov.platform.profileservice.profile.domain.services;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetVehicleByOwnerIdOrVehicleIdQuery;

import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByOwnerIdOrVehicleIdQuery query);
}
