package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.EDay;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;

import java.util.UUID;

public class ClassScheduleFromResourceAssembler {

    public static ClassSchedule toEntity(ClassScheduleResource resource) {
        if (resource == null) {
            return null;
        }

        String id = (resource.id() != null && !resource.id().isBlank()) ? resource.id() : UUID.randomUUID().toString();

        return new ClassSchedule(
                id,
                resource.courseName(),
                resource.locationName(),
                resource.latitude(),
                resource.longitude(),
                resource.address(),
                resource.startedAt(),
                resource.endedAt(),
                EDay.valueOf(resource.selectedDay().toUpperCase())
        );
    }
}
