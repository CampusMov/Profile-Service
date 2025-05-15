package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.VehicleStatusResource;

public class VehicleStatusResourceFromValueAssembler {
    public static VehicleStatusResource toResource(String vehicleStatus) {
        return new VehicleStatusResource(vehicleStatus);
    }
}
