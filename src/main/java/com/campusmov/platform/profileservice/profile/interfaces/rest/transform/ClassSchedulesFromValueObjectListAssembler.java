package com.campusmov.platform.profileservice.profile.interfaces.rest.transform;

import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;

import java.util.List;
import java.util.stream.Collectors;

public class ClassSchedulesFromValueObjectListAssembler {

    public static List<ClassScheduleResource> toResources(List<ClassSchedule> classSchedules) {
        return classSchedules.stream()
                .map(classSchedule -> new ClassScheduleResource(
                        classSchedule.getId(),
                        classSchedule.getCourseName(),
                        classSchedule.getLocation().name(),
                        classSchedule.getLocation().address(),
                        classSchedule.getStartedAt(),
                        classSchedule.getEndedAt(),
                        classSchedule.getSelectedDay().name()
                ))
                .collect(Collectors.toList());
    }
}