package com.campusmov.platform.profileservice.profile.domain.model.entities;

import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoriteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(name = "stop_name")
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "location_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "location_longitude")),
            @AttributeOverride(name = "name", column = @Column(name = "location_name"))
    })
    private Location location;

    @NotNull
    @Column(name = "stop_address")
    private String address;

    @NotNull
    private String description;

    protected FavoriteStop() {
        // JPA
    }

    public FavoriteStop(String id,
                        String name,
                        Location location,
                        String address,
                        String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }

        this.id = id;
        this.name = name;
        this.location = location;
        this.address = address;
        this.description = description;
    }
}