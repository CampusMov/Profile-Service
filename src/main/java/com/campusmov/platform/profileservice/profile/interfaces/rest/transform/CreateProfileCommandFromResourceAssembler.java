package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateProfileCommand;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateProfileResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreateProfileCommandFromResourceAssembler {

    public static CreateProfileCommand toCommand(CreateProfileResource resource) {
        Optional<List<ClassSchedule>> classSchedules = resource.classSchedules()
                .map(schedules -> schedules.stream()
                        .map(CreateProfileCommandFromResourceAssembler::toClassSchedule)
                        .collect(Collectors.toList()));

        return new CreateProfileCommand(
                resource.userId(),
                resource.institutionalEmailAddress(),
                resource.personalEmailAddress(),
                resource.countryCode(),
                resource.phoneNumber(),
                resource.firstName(),
                resource.lastName(),
                LocalDate.parse(resource.birthDate()),
                resource.gender(),
                resource.profilePictureUrl(),
                resource.university(),
                resource.faculty(),
                resource.academicProgram(),
                resource.semester(),
                classSchedules
        );
    }

    private static ClassSchedule toClassSchedule(CreateClassScheduleResource resource) {
        return new ClassSchedule(
                CreateClassScheduleCommandFromResourceAssembler.toCommand(resource)
        );
    }
}