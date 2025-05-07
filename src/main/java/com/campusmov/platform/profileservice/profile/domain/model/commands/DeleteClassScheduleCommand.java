package com.campusmov.platform.profileservice.profile.domain.model.commands;

public record DeleteClassScheduleCommand(
        String classScheduleId,
        String profileId
) {
    public DeleteClassScheduleCommand {
        if (classScheduleId == null || classScheduleId.isBlank()) {
            throw new IllegalArgumentException("Class schedule ID cannot be null or blank");
        }
        if (profileId == null || profileId.isBlank()) {
            throw new IllegalArgumentException("Profile ID cannot be null or blank");
        }
    }
}
