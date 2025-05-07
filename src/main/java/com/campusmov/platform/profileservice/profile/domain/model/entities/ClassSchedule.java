package com.campusmov.platform.profileservice.profile.domain.model.entities;

import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.EDay;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.Location;
import com.campusmov.platform.profileservice.shared.domain.model.entities.AuditableModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDateTime;

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
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private EDay selectedDay;

    protected ClassSchedule() {
        //Constructor por defecto para JPA
    }

    public ClassSchedule(String id,
                          String courseName,
                          Location location,
                          LocalDateTime startedAt,
                          LocalDateTime endedAt,
                          EDay selectedDay) {
        if (courseName == null || courseName.isBlank()) {
            throw new IllegalArgumentException("courseName cannot be null or blank");
        }
        if (location == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        if (endedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("endedAt cannot be before startedAt");
        }
        if (selectedDay == null) {
            throw new IllegalArgumentException("selectedDay cannot be null");
        }
        this.id = id;
        this.courseName = courseName;
        this.location = location;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.selectedDay = selectedDay;
    }
}
