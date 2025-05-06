package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record ProfileResource(
        Long id,
        // ContactInformation
        String emailAddress,
        String countryCode,
        String phoneNumber,
        String street,
        String city,
        String postalCode,
        String country,
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
        String semester
) {}
