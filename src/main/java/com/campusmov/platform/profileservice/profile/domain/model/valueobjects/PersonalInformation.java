package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Embeddable
public record PersonalInformation(
        String firstName,
        String lastName,
        LocalDate birthDate,
        @Enumerated(EnumType.STRING)
        EGender gender
) {
    public PersonalInformation {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
    }
}