package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ProfileResource;

import java.util.stream.Collectors;

public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResource(Profile profile) {
        return new ProfileResource(
                // id
                profile.getId().id(),

                // contactInformation
                profile.getContactInformation().email().institutionalEmailAddress(),
                profile.getContactInformation().email().personalEmailAddress(),
                profile.getContactInformation().phone().countryCode(),
                profile.getContactInformation().phone().number(),

                // personalInformation
                profile.getPersonalInformation().firstName(),
                profile.getPersonalInformation().lastName(),
                profile.getPersonalInformation().birthDate(),
                profile.getPersonalInformation().gender().name(),

                // profilePictureUrl
                profile.getProfilePictureUrl(),

                // academicInformation
                profile.getAcademicInformation().getUniversity(),
                profile.getAcademicInformation().getFaculty(),
                profile.getAcademicInformation().getAcademicProgram(),
                profile.getAcademicInformation().getSemester(),

                // classSchedules
                profile.getAcademicInformation().getClassSchedules().stream()
                        .map(ClassScheduleResourceFromEntityAssembler::toResource)
                        .collect(Collectors.toList())
        );
    }
}