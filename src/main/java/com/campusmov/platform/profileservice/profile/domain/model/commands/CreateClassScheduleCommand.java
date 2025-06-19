package com.campusmov.platform.profileservice.profile.domain.model.commands;

import java.time.LocalTime;

public record CreateClassScheduleCommand(
        String courseName,
        String locationName,
        Double latitude,
        Double longitude,
        String address,
        LocalTime startedAt,
        LocalTime endedAt,
        String selectedDay
) {
    public CreateClassScheduleCommand {
        if (courseName == null || courseName.isBlank()) {
            throw new IllegalArgumentException("courseName cannot be null or blank");
        }
        if (selectedDay == null || selectedDay.isBlank()) {
            throw new IllegalArgumentException("selectedDay cannot be null or blank");
        }
        if (startedAt == null) {
            throw new IllegalArgumentException("startedAt cannot be null");
        }
        if (endedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("endedAt cannot be before startedAt");
        }
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("latitude and longitude cannot be null");
        }
    }
}