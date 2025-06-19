package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalTime;

public record UpdateClassScheduleResource(
        String courseName,
        String locationName,
        Double locationLatitude,
        Double locationLongitude,
        String address,
        LocalTime startedAt,
        LocalTime endedAt,
        String selectedDay
) {
}