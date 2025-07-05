package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateProfileCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateProfileResource;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule; // Import ClassSchedule

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommand(UpdateProfileResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("UpdateProfileResource cannot be null");
        }

        Optional<List<ClassSchedule>> classSchedules = Optional.ofNullable(resource.classSchedules())
                .map(list -> list.stream()
                        .map(ClassScheduleFromResourceAssembler::toEntity)
                        .collect(Collectors.toList()));

        return new UpdateProfileCommand(
                resource.institutionalEmailAddress(),
                resource.personalEmailAddress(),
                resource.countryCode(),
                resource.phoneNumber(),
                resource.firstName(),
                resource.lastName(),
                resource.birthDate(),
                resource.gender(),
                resource.profilePictureUrl(),
                resource.university(),
                resource.faculty(),
                resource.academicProgram(),
                resource.semester(),
                classSchedules
        );
    }
}