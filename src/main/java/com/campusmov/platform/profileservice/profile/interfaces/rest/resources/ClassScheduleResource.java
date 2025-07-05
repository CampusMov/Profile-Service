package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalTime;

public record ClassScheduleResource(
        String id,
        String courseName,
        String locationName,
        String address,
        Double latitude,
        Double longitude,
        LocalTime startedAt,
        LocalTime endedAt,
        String selectedDay
) {
}
