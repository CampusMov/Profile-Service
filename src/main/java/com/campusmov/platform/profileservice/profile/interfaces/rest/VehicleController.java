package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.domain.model.queries.GetVehicleByOwnerIdOrVehicleIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleCommandService;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ChangeVehicleStatusResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateVehicleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.VehicleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.VehicleStatusResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.ChangeVehicleStatusCommandFromResourceAssembler;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.VehicleStatusResourceFromValueAssembler;
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
    private final VehicleCommandService vehicleCommandService;

    public VehicleController(VehicleQueryService vehicleQueryService, VehicleCommandService vehicleCommandService) {
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleCommandService = vehicleCommandService;
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


    @PostMapping
    @Operation(summary = "Create a new vehicle")
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource createVehicleResource) {
        var createVehicleCommand = CreateVehicleCommandFromResourceAssembler.toCommand(createVehicleResource);
        var createdVehicle = vehicleCommandService.handle(createVehicleCommand);
        if (createdVehicle.isEmpty()) return ResponseEntity.badRequest().build();
        var createdVehicleResource = VehicleResourceFromEntityAssembler.toResource(createdVehicle.get());
        return ResponseEntity.status(201).body(createdVehicleResource);
    }

    @PutMapping("/{vehicleId}/change-status")
    @Operation(summary = "Change vehicle status")
    public ResponseEntity<VehicleStatusResource> changeVehicleStatus(@PathVariable String vehicleId, @RequestBody ChangeVehicleStatusResource changeVehicleStatusResource) {
        var changeVehicleStatusCommand = ChangeVehicleStatusCommandFromResourceAssembler.toCommand(changeVehicleStatusResource);
        var vehicleStatus = vehicleCommandService.handle(changeVehicleStatusCommand, vehicleId);
        if (vehicleStatus.isEmpty()) return ResponseEntity.notFound().build();
        var vehicleStatusResource = VehicleStatusResourceFromValueAssembler.toResource(vehicleStatus.get());
        return ResponseEntity.ok(vehicleStatusResource);
    }
}
