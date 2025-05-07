package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
@Getter
@Setter
public class AcademicInformation {

    @NotNull
    private String university;

    @NotNull
    private String faculty;

    @NotNull
    private String academicProgram;

    @NotNull
    private String semester;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id", nullable = false)
    private List<ClassSchedule> classSchedules = new ArrayList<>();

    public AcademicInformation(String university, String faculty, String academicProgram, String semester) {
        if (university == null || university.isBlank()) {
            throw new IllegalArgumentException("University cannot be null or blank");
        }
        if (faculty == null || faculty.isBlank()) {
            throw new IllegalArgumentException("Faculty cannot be null or blank");
        }
        if (academicProgram == null || academicProgram.isBlank()) {
            throw new IllegalArgumentException("Academic program cannot be null or blank");
        }
        if (semester == null || semester.isBlank()) {
            throw new IllegalArgumentException("Semester cannot be null or blank");
        }
        this.university = university;
        this.faculty = faculty;
        this.academicProgram = academicProgram;
        this.semester = semester;
    }

    public AcademicInformation() {
        //Constructor por defecto para JPA
    }

    public void addClassSchedule(CreateClassScheduleCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        ClassSchedule classSchedule = new ClassSchedule(command);
        this.classSchedules.add(classSchedule);
    }

    public Optional<ClassSchedule> updateClassSchedule(String classScheduleId, UpdateClassScheduleCommand command) {
        if (classScheduleId == null || classScheduleId.isBlank()) {
            throw new IllegalArgumentException("Class schedule ID cannot be null or blank");
        }
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        for (ClassSchedule classSchedule : this.classSchedules) {
            if (classSchedule.getId().equals(classScheduleId)) {
                return classSchedule.updateClassScheduleInfo(command);
            }
        }
        throw new IllegalArgumentException("Class schedule not found");
    }

    public boolean removeClassScheduleByClassScheduleId(String classScheduleId) {
        if (classScheduleId == null || classScheduleId.isBlank()) {
            throw new IllegalArgumentException("Class schedule ID cannot be null or blank");
        }
        return this.classSchedules.removeIf(classSchedule -> classSchedule.getId().equals(classScheduleId));
    }
}