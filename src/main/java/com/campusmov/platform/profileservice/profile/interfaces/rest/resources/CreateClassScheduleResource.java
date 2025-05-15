package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateClassScheduleResource(
        String courseName,
        String locationName,
        String address,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String selectedDay
) {
}
