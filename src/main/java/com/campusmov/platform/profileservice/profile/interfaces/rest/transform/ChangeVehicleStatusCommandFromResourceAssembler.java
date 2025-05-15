package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.ChangeVehicleStatusCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ChangeVehicleStatusResource;

public class ChangeVehicleStatusCommandFromResourceAssembler {
    public static ChangeVehicleStatusCommand toCommand(ChangeVehicleStatusResource resource) {
        return new ChangeVehicleStatusCommand(resource.vehicleStatus());
    }
}
