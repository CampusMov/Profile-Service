package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.domain.model.queries.GetVehicleByOwnerIdOrVehicleIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.VehicleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController(value = "VehicleController")
@RequestMapping("/api/v1/vehicle")
@Tag(name = "Vehicle", description = "Vehicle API")
public class VehicleController {
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleQueryService vehicleQueryService) {
        this.vehicleQueryService = vehicleQueryService;
    }

    @GetMapping
    @Operation(summary = "Get vehicle by ownerId or vehicleId")
    public ResponseEntity<VehicleResource> getVehicleByOwnerIdOrVehicleId(@RequestParam Optional<String> ownerId,
                                                                          @RequestParam Optional<String> vehicleId) {
        var getVehicleByOwnerIdQuery = new GetVehicleByOwnerIdOrVehicleIdQuery(ownerId, vehicleId);
        var vehicle = vehicleQueryService.handle(getVehicleByOwnerIdQuery);

        if (vehicle.isEmpty()) return ResponseEntity.notFound().build();

        var vehicleResources = VehicleResourceFromEntityAssembler.toResource(vehicle.get());
        return ResponseEntity.ok(vehicleResources);
    }

}
