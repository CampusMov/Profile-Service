package com.campusmov.platform.profileservice.profile.interfaces.rest;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.*;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profiles")
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfileController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get profile by ID")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable String id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResource(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @PostMapping
    @Operation(summary = "Create a new profile")
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource createProfileResource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommand(createProfileResource);
        var createdProfile = profileCommandService.handle(createProfileCommand);
        if (createdProfile.isEmpty()) return ResponseEntity.badRequest().build();
        var createdProfileResource = ProfileResourceFromEntityAssembler.toResource(createdProfile.get());
        return ResponseEntity.status(201).body(createdProfileResource);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing profile")
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable String id, @RequestBody UpdateProfileResource updateProfileResource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommand(updateProfileResource);
        var updatedProfile = profileCommandService.handle(updateProfileCommand, id);
        if (updatedProfile.isEmpty()) return ResponseEntity.notFound().build();
        var updatedProfileResource = ProfileResourceFromEntityAssembler.toResource(updatedProfile.get());
        return ResponseEntity.ok(updatedProfileResource);
    }
}