package com.campusmov.platform.profileservice.profile.domain.model.commands;

import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UpdateProfileCommand(
        String institutionalEmailAddress,
        String personalEmailAddress,
        String countryCode,
        String phoneNumber,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String profilePictureUrl,
        String university,
        String faculty,
        String academicProgram,
        String semester,

        Optional<List<ClassSchedule>> classSchedules
) {
}