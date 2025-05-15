package com.campusmov.platform.profileservice.profile.domain.services;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.commands.ChangeVehicleStatusCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateVehicleCommand;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand command);
    Optional<String> handle(ChangeVehicleStatusCommand command,  String vehicleId);
}
