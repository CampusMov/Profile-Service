package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateClassScheduleResource(
        String courseName,
        String locationName,
        double latitude,
        double longitude,
        String address,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String selectedDay
) {
}
