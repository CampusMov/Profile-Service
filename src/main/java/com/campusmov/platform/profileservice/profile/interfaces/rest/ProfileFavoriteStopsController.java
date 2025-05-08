package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateFavoriteStopResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.FavoriteStopResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateFavoriteStopResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles/{id}/favorite-stops")
@Tag(name = "Profiles Favorite Stops", description = "Available Profile Favorite Stops Endpoints")
public class ProfileFavoriteStopsController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileFavoriteStopsController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    @GetMapping
    @Operation(summary = "Get favorite stops by profile ID")
    public ResponseEntity<List<FavoriteStopResource>> getFavoriteStopsByProfileId(@PathVariable String id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var favoriteStops = FavoriteStopsFromEntityListAssembler.toResources(profile.get().getFavoriteStops());
        return ResponseEntity.ok(favoriteStops);
    }

}
