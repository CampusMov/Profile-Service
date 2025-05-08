package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record ProfileResource(
        String id,
        // ContactInformation
        String institutionalEmailAddress,
        String personalEmailAddress,
        String countryCode,
        String phoneNumber,
        // PersonalInformation
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,

        String profilePictureUrl,

        // AcademicInformation
        String university,
        String faculty,
        String academicProgram,
        String semester,
        // ClassSchedule
        List<ClassScheduleResource> classSchedules
) {}
