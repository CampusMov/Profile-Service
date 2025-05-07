package com.campusmov.platform.profileservice.profile.domain.model.commands;

import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateClassScheduleResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record CreateProfileCommand(
        String userId,
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
        // Perfil
        String profilePictureUrl,
        // AcademicInformation
        String university,
        String faculty,
        String academicProgram,
        String semester,
        Optional<List<ClassSchedule>> classSchedules
) {
}

