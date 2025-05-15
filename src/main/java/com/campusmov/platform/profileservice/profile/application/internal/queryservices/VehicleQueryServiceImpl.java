package com.campusmov.platform.profileservice.profile.application.internal.queryservices;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetVehicleByOwnerIdOrVehicleIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleQueryService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {
    private final VehicleRepository vehicleRepository;

    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByOwnerIdOrVehicleIdQuery query) {
        if (query.ownerId().isPresent()) {
            return Optional.of(vehicleRepository.findByOwnerId(new UserId(query.ownerId().get())));
        } else if (query.vehicleId().isPresent()) {
            return vehicleRepository.findById(query.vehicleId().get());
        }
        return Optional.empty();
    }
}
