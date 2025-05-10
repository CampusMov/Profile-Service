package com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByOwnerId(UserId ownerId);
    boolean existsByVehicleIdentification_Vin(String vin);
    boolean existsByOwnerId(UserId ownerId);
}
