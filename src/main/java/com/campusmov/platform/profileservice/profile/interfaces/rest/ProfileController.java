package com.campusmov.platform.profileservice.profile.interfaces.rest;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateProfileResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ProfileResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get profile by ID")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResource(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @PostMapping
    @Operation(summary = "Create a new profile")
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource createProfileResource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.fromResource(createProfileResource);
        var createdProfile = profileCommandService.handle(createProfileCommand);
        if (createdProfile.isEmpty()) return ResponseEntity.badRequest().build();
        var createdProfileResource = ProfileResourceFromEntityAssembler.toResource(createdProfile.get());
        return ResponseEntity.status(201).body(createdProfileResource);
    }
}