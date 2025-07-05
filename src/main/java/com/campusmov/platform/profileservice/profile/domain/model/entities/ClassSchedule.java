package com.campusmov.platform.profileservice.profile.domain.model.entities;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.Coordinates;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.EDay;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.Location;
import com.campusmov.platform.profileservice.shared.domain.model.entities.AuditableModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClassSchedule extends AuditableModel {
    @Id
    private String id;

    private String courseName;

    @Embedded
    private Location location;

    @NotNull
    private LocalTime startedAt;

    @NotNull
    private LocalTime endedAt;

    @Enumerated(EnumType.STRING)
    private EDay selectedDay;

    public ClassSchedule(String id, String courseName, String locationName, double latitude, double longitude, String address, LocalTime startedAt, LocalTime endedAt, EDay selectedDay) {
        this.id = id;
        this.courseName = courseName;
        this.location = new Location(
                locationName,
                new Coordinates(latitude, longitude),
                address
        );
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.selectedDay = selectedDay;
    }

    public ClassSchedule(CreateClassScheduleCommand command) {
        this(UUID.randomUUID().toString(),
                command.courseName(),
                command.locationName(),
                command.latitude(),
                command.longitude(),
                command.address(),
                command.startedAt(),
                command.endedAt(),
                EDay.valueOf(command.selectedDay().toUpperCase()));
    }

    public Optional<ClassSchedule> updateClassScheduleInfo(UpdateClassScheduleCommand command) {
        if (command.courseName() != null && !command.courseName().isBlank()) {
            this.setCourseName(command.courseName());
        }
        if (command.locationName() != null && !command.locationName().isBlank() &&
                command.address() != null && !command.address().isBlank()) {
            this.setLocation(new Location(
                    command.locationName(),
                    new Coordinates(command.locationLatitude(), command.locationLongitude()),
                    command.address()
            ));
        }

        if (command.startedAt() != null) {
            this.setStartedAt(command.startedAt());
        }
        if (command.endedAt() != null) {
            this.setEndedAt(command.endedAt());
        }
        if (command.selectedDay() != null) {
            this.setSelectedDay(EDay.valueOf(command.selectedDay().toUpperCase()));
        }
        return Optional.of(this);
    }
}