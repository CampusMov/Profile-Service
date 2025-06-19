package com.campusmov.platform.profileservice.profile.domain.model.commands;

import java.time.LocalTime;

public record UpdateClassScheduleCommand(
        String courseName,
        String locationName,
        Double locationLatitude,
        Double locationLongitude,
        String address,
        LocalTime startedAt,
        LocalTime endedAt,
        String selectedDay
) { }
