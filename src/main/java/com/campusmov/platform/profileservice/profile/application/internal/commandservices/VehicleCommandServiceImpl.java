package com.campusmov.platform.profileservice.profile.application.internal.commandservices;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.commands.ChangeVehicleStatusCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateVehicleCommand;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleCommandService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {
    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        Vehicle vehicle = Vehicle.from(command);
        if (vehicleRepository.existsByVehicleIdentification_Vin(vehicle.getVehicleIdentification().vin())
                || vehicleRepository.existsByOwnerId(vehicle.getOwnerId())) {
            return Optional.empty();
        } else {
            vehicleRepository.save(vehicle);
            return Optional.of(vehicle);
        }

    }

    @Override
    public Optional<String> handle(ChangeVehicleStatusCommand command, String vehicleId) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle.isPresent()) {
            vehicle.get().changeStatus(command.vehicleStatus());
            vehicleRepository.save(vehicle.get());
            return Optional.of(vehicle.get().getStatus().name());
        } else {
            return Optional.empty();
        }
    }
}
