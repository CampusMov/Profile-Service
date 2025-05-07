package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateProfileResource(
        String institutionalEmailAddress,
        String personalEmailAddress,
        String countryCode,
        String phoneNumber,
        // PersonalInformation
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        // Perfil
        String profilePictureUrl,
        // AcademicInformation
        String university,
        String faculty,
        String academicProgram,
        String semester
) {
}
