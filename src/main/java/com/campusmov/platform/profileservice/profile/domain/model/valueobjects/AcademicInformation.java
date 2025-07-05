package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateClassScheduleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
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

    public void updateAcademicInformationInfo(String university, String faculty, String academicProgram, String semester) {
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
        this.setUniversity(university);
        this.setFaculty(faculty);
        this.setAcademicProgram(academicProgram);
        this.setSemester(semester);
    }

    public void updateClassSchedules(List<ClassSchedule> incomingSchedules) {
        if (incomingSchedules == null) {
            this.classSchedules.clear();
            return;
        }

        List<ClassSchedule> schedulesToRemove = this.classSchedules.stream()
                .filter(current -> incomingSchedules.stream().noneMatch(inc -> inc.getId().equals(current.getId())))
                .collect(Collectors.toList());
        this.classSchedules.removeAll(schedulesToRemove);

        incomingSchedules.forEach(incoming -> {
            Optional<ClassSchedule> existing = this.classSchedules.stream()
                    .filter(current -> current.getId().equals(incoming.getId()))
                    .findFirst();

            if (existing.isPresent()) {
                existing.get().updateClassScheduleInfo(
                        new UpdateClassScheduleCommand(
                                incoming.getCourseName(),
                                incoming.getLocation().name(),
                                incoming.getLocation().coordinates().latitude(),
                                incoming.getLocation().coordinates().longitude(),
                                incoming.getLocation().address(),
                                incoming.getStartedAt(),
                                incoming.getEndedAt(),
                                incoming.getSelectedDay().toString()
                        )
                );
            } else {
                this.classSchedules.add(incoming);
            }
        });
    }

    public ClassSchedule addClassSchedule(CreateClassScheduleCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        ClassSchedule classSchedule = new ClassSchedule(command);
        this.classSchedules.add(classSchedule);
        return classSchedule;
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