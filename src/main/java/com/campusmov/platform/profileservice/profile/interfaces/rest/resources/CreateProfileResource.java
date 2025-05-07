package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.util.List;
import java.util.Optional;

public record CreateProfileResource(
        String userId,
        String institutionalEmailAddress,
        String personalEmailAddress,
        String countryCode,
        String phoneNumber,
        String firstName,
        String lastName,
        String birthDate,
        String gender,
        String profilePictureUrl,
        String university,
        String faculty,
        String academicProgram,
        String semester,
        Optional<List<CreateClassScheduleResource>> classSchedules
) {
}