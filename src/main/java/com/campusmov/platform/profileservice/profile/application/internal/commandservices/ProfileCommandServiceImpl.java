package com.campusmov.platform.profileservice.profile.application.internal.commandservices;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.commands.*;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        Profile profile = Profile.from(command);
        if (profileRepository.existsById(profile.getId())) {
            return Optional.empty(); //Profile already exists
        } else {
            profileRepository.save(profile);
            return Optional.of(profile); //Profile created successfully
        }
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command, String profileId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.updateProfile(command);
        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public Optional<ClassSchedule> handle(CreateClassScheduleCommand command, String profileId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.addClassScheduleToAcademicInformation(command);
        profileRepository.save(profile);
        return profile.getAcademicInformation().getClassSchedules()
                .stream()
                .filter(classSchedule -> classSchedule.getCourseName().equals(command.courseName()))
                .findFirst();
    }

    @Override
    public Optional<ClassSchedule> handle(UpdateClassScheduleCommand command, String profileId, String classScheduleId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        Optional<ClassSchedule> updatedClassSchedule = profile.updateClassSchedule(classScheduleId, command);
        profileRepository.save(profile);

        return updatedClassSchedule;
    }

    @Override
    public void handle(DeleteClassScheduleCommand command) {
        UserId userId = new UserId(command.profileId());
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        boolean removed = profile.removeClassScheduleByClassScheduleId(command.classScheduleId());
        if (!removed) {
            throw new IllegalArgumentException("Class schedule not found");
        }
        profileRepository.save(profile);
    }

}
