package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UpdateProfileResource(
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
        List<ClassScheduleResource> classSchedules
) {
}