package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record ContactInformation(

        @Embedded
        Email email,

        @Embedded
        PhoneNumber phone,

        @Embedded
        Address address

) {
    public ContactInformation {
        if (email   == null) throw new IllegalArgumentException("Email es requerido");
        if (phone   == null) throw new IllegalArgumentException("PhoneNumber es requerido");
        if (address == null) throw new IllegalArgumentException("Address es requerido");
    }

}
