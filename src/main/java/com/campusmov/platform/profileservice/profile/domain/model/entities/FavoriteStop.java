package com.campusmov.platform.profileservice.profile.domain.model.entities;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateFavoriteStopCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateFavoriteStopCommand;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.Coordinates;
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

    @NotNull
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "location_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "location_longitude")),
            @AttributeOverride(name = "name", column = @Column(name = "location_name"))
    })
    private Location location;

    protected FavoriteStop() {
        // JPA
    }

    public FavoriteStop(CreateFavoriteStopCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (command.description() == null || command.description().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }

        this.name = command.name();
        this.description = command.description();
        this.location = new Location(
                command.locationName(),
                new Coordinates(command.locationLatitude(), command.locationLongitude()),
                command.address()
        );
    }

    public void updateFavoriteStopInfo(UpdateFavoriteStopCommand command) {
        if (command.name() != null && !command.name().isBlank()) {
            this.name = command.name();
        }
        if (command.description() != null && !command.description().isBlank()) {
            this.description = command.description();
        }
        if (command.locationName() != null && !command.locationName().isBlank()) {
            this.location = new Location(
                    command.locationName(),
                    new Coordinates(command.locationLatitude(), command.locationLongitude()),
                    command.address()
            );
        }
    }

    public boolean removeFavoriteStopByFavoriteStopId(String favoriteStopId) {
        return this.id.equals(favoriteStopId);
    }
}