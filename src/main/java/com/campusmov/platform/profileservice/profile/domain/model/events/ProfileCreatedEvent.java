package com.campusmov.platform.profileservice.profile.domain.model.events;

import com.campusmov.platform.profileservice.shared.domain.model.events.DomainEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

@Getter
public final class ProfileCreatedEvent extends ApplicationEvent implements DomainEvent, ProfileEvent {
    private final String profileId;
    private final String institutionalEmailAddress;
    private final String personalEmailAddress;
    private final String countryCode;
    private final String number;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String gender;
    private final String profilePictureUrl;
    private final String university;
    private final String faculty;
    private final String academicProgram;
    private final String semester;

    public ProfileCreatedEvent(
            Object source,
            String profileId,
            String institutionalEmailAddress,
            String personalEmailAddress,
            String countryCode,
            String number,
            String firstName,
            String lastName,
            LocalDate birthDate,
            String gender,
            String profilePictureUrl,
            String university,
            String faculty,
            String academicProgram,
            String semester
    ) {
        super(source);
        this.profileId = profileId;
        this.institutionalEmailAddress = institutionalEmailAddress;
        this.personalEmailAddress = personalEmailAddress;
        this.countryCode = countryCode;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profilePictureUrl = profilePictureUrl;
        this.university = university;
        this.faculty = faculty;
        this.academicProgram = academicProgram;
        this.semester = semester;
    }
}
