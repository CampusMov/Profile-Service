package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResource(Profile profile) {
        return new ProfileResource(
                // id
                profile.getId().id(),

                // contactInformation
                profile.getContactInformation().email().emailAddress(),
                profile.getContactInformation().phone().countryCode(),
                profile.getContactInformation().phone().number(),
                profile.getContactInformation().address().street(),
                profile.getContactInformation().address().city(),
                profile.getContactInformation().address().postalCode(),
                profile.getContactInformation().address().country(),

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
                profile.getAcademicInformation().getSemester()
        );
    }
}
