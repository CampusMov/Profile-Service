package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDateTime;

public record UpdateClassScheduleResource(
        String courseName, String locationName, double locationLatitude,
        double locationLongitude, String address, LocalDateTime startedAt,
        LocalDateTime endedAt, String selectedDay
) {
}