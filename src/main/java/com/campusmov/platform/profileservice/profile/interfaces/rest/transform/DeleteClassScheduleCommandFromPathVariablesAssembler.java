package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.DeleteClassScheduleCommand;

public class DeleteClassScheduleCommandFromPathVariablesAssembler {
    public static DeleteClassScheduleCommand toCommand(String profileId, String classScheduleId) {
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("Profile ID cannot be null or blank");
        }
        if (classScheduleId == null || classScheduleId.isBlank()) {
            throw new IllegalArgumentException("Class Schedule ID cannot be null or blank");
        }
        return new DeleteClassScheduleCommand(profileId, classScheduleId);
    }
}
