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
        boolean vehicleWithVinExists = vehicleRepository.existsByVehicleIdentification_Vin(vehicle.getVehicleIdentification().vin());
        boolean vehicleWithOwnerIdExists = vehicleRepository.existsByOwnerId(vehicle.getOwnerId());
        if (vehicleWithVinExists) throw new IllegalArgumentException("Vehicle with VIN already exists.");
        if (vehicleWithOwnerIdExists) throw new IllegalArgumentException("Vehicle with this owner already exists.");
        try {
            vehicleRepository.save(vehicle);
            return Optional.of(vehicle);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> handle(ChangeVehicleStatusCommand command, String vehicleId) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle.isEmpty()) throw new IllegalArgumentException("Vehicle not found with ID: " + vehicleId);
        vehicle.get().changeStatus(command.vehicleStatus());
        try {
            vehicleRepository.save(vehicle.get());
            return Optional.of(vehicle.get().getStatus().name());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
