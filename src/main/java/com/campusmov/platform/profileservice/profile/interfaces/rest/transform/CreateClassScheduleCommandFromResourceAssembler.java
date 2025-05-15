package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateClassScheduleResource;

public class CreateClassScheduleCommandFromResourceAssembler {
    public static CreateClassScheduleCommand toCommand(CreateClassScheduleResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("ClassScheduleResource cannot be null");
        }
        return new CreateClassScheduleCommand(
                resource.courseName(),
                resource.locationName(),
                19202,
                33823,
                resource.address(),
                resource.startedAt(),
                resource.endedAt(),
                resource.selectedDay()
        );
    }
}