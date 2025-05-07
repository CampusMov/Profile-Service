package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateClassScheduleResource;

public class UpdateClassScheduleCommandFromResourceAssembler {
    public static UpdateClassScheduleCommand toCommand(UpdateClassScheduleResource updateClassScheduleResource) {
        return new UpdateClassScheduleCommand(
                updateClassScheduleResource.courseName(),
                updateClassScheduleResource.locationName(),
                updateClassScheduleResource.locationLatitude(),
                updateClassScheduleResource.locationLongitude(),
                updateClassScheduleResource.address(),
                updateClassScheduleResource.startedAt(),
                updateClassScheduleResource.endedAt(),
                updateClassScheduleResource.selectedDay()
        );
    }
}
