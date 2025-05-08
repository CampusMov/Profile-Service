package com.campusmov.platform.profileservice.profile.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateClassScheduleCommand(
        String courseName, String locationName, double locationLatitude,
        double locationLongitude, String address, LocalDateTime startedAt,
        LocalDateTime endedAt, String selectedDay
) { }
