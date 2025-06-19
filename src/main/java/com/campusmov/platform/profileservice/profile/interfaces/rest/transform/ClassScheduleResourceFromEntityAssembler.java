package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;

public class ClassScheduleResourceFromEntityAssembler {
    public static ClassScheduleResource toResource(ClassSchedule classSchedule) {
        if (classSchedule == null) {
            throw new IllegalArgumentException("ClassSchedule cannot be null");
        }
        return new ClassScheduleResource(
                classSchedule.getId(),
                classSchedule.getCourseName(),
                classSchedule.getLocation().name(),
                classSchedule.getLocation().address(),
                classSchedule.getLocation().coordinates().latitude(),
                classSchedule.getLocation().coordinates().longitude(),
                classSchedule.getStartedAt(),
                classSchedule.getEndedAt(),
                classSchedule.getSelectedDay().name()
        );
    }
}