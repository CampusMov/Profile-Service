package com.campusmov.platform.profileservice.profile.domain.model.commands;

import java.time.LocalDateTime;

public record CreateClassScheduleCommand(
        String courseName,
        String locationName,
        double latitude,
        double longitude,
        String address,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String selectedDay
) {}