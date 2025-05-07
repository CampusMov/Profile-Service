package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileQueryService;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/profiles/{id}/class-schedules")
@Tag(name = "Profiles Class Schedules", description = "Available Profile Class Schedules Endpoints")
public class ProfileClassSchedulesController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileClassSchedulesController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    @GetMapping
    @Operation(summary = "Get class schedules by profile ID")
    public ResponseEntity<List<ClassScheduleResource>> getClassSchedulesByProfileId(@PathVariable String id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var classSchedules = ClassSchedulesFromValueObjectListAssembler.toResources(profile.get().getAcademicInformation().getClassSchedules());
        return ResponseEntity.ok(classSchedules);
    }

    @PostMapping
    @Operation(summary = "Add a class schedule to a profile")
    public ResponseEntity<ClassScheduleResource> addClassSchedule(@PathVariable String id, @RequestBody CreateClassScheduleResource createClassScheduleResource) {
        var createClassScheduleCommand = CreateClassScheduleCommandFromResourceAssembler.toCommand(createClassScheduleResource);
        var classSchedule = profileCommandService.handle(createClassScheduleCommand, id);
        if (classSchedule.isEmpty()) return ResponseEntity.badRequest().build();
        var classScheduleResourceResponse = ClassScheduleResourceFromEntityAssembler.toResource(classSchedule.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(classScheduleResourceResponse);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Update a class schedule")
    public ResponseEntity<ClassScheduleResource> updateClassSchedule(@PathVariable String id, @PathVariable String scheduleId, @RequestBody UpdateClassScheduleResource updateClassScheduleResource) {
        var updateClassScheduleCommand = UpdateClassScheduleCommandFromResourceAssembler.toCommand(updateClassScheduleResource);
        var classSchedule = profileCommandService.handle(updateClassScheduleCommand, id, String.valueOf(scheduleId));
        if (classSchedule.isEmpty()) return ResponseEntity.badRequest().build();
        var classScheduleResourceResponse = ClassScheduleResourceFromEntityAssembler.toResource(classSchedule.get());
        return ResponseEntity.status(HttpStatus.OK).body(classScheduleResourceResponse);
    }
}
