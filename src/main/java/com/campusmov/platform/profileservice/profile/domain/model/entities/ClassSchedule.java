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
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Entity
@Getter
public class ClassSchedule extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public ClassSchedule() {
        super();
    }

    public ClassSchedule(CreateClassScheduleCommand command) {
        this();
        this.courseName = command.courseName();
        this.location = new Location(
                command.locationName(),
                new Coordinates(command.latitude(), command.longitude()),
                command.address()
        );
        this.startedAt = command.startedAt();
        this.endedAt = command.endedAt();
        this.selectedDay = EDay.valueOf(command.selectedDay().toUpperCase());
    }

    public Optional<ClassSchedule> updateClassScheduleInfo(UpdateClassScheduleCommand command) {
        if (command.courseName() != null && !command.courseName().isBlank()) {
            this.courseName = command.courseName();
        }
        if (command.locationName() != null && !command.locationName().isBlank() &&
                command.locationLatitude() != 0 && command.locationLongitude() != 0 &&
                command.address() != null && !command.address().isBlank()) {
            this.location = new Location(
                    command.locationName(),
                    new Coordinates(command.locationLatitude(), command.locationLongitude()),
                    command.address()
            );
        }
        if (command.startedAt() != null) {
            this.startedAt = command.startedAt();
        }
        if (command.endedAt() != null) {
            this.endedAt = command.endedAt();
        }
        if (command.selectedDay() != null) {
            this.selectedDay = EDay.valueOf(command.selectedDay().toUpperCase());
        }
        return Optional.of(this);
    }

}
