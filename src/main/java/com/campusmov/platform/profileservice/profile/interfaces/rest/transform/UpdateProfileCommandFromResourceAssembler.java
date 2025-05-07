package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateProfileCommand;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommand(UpdateProfileResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("UpdateProfileResource cannot be null");
        }
        return new UpdateProfileCommand(
                resource.institutionalEmailAddress(),
                resource.personalEmailAddress(),
                resource.countryCode(),
                resource.phoneNumber(),
                // PersonalInformation
                resource.firstName(),
                resource.lastName(),
                resource.birthDate(),
                resource.gender(),
                // Perfil
                resource.profilePictureUrl(),
                // AcademicInformation
                resource.university(),
                resource.faculty(),
                resource.academicProgram(),
                resource.semester()
        );
    }
}
