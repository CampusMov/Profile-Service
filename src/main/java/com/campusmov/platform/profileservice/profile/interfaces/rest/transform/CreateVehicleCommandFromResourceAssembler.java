package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateVehicleCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommand(CreateVehicleResource resource) {
        return new CreateVehicleCommand(
                resource.brand(),
                resource.model(),
                resource.year(),
                resource.status(),
                resource.vin(),
                resource.licensePlate(),
                resource.ownerId()
        );
    }
}
