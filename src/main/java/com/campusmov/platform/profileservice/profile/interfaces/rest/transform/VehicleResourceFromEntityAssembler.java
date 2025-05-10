package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {

    public static VehicleResource toResource(Vehicle vehicle) {
        return new VehicleResource(
                vehicle.getId(),
                vehicle.getBasicVehicleSpecs().brand(),
                vehicle.getBasicVehicleSpecs().model(),
                vehicle.getBasicVehicleSpecs().year(),
                vehicle.getStatus().name(),
                vehicle.getOwnerId().id(),
                vehicle.getVehicleIdentification().vin(),
                vehicle.getVehicleIdentification().licensePlate()
        );
    }
}

