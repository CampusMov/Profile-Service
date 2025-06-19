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
        Profile profile = new Profile(command);
        var existingProfile = profileRepository.findById(profile.getId());
        if (existingProfile.isPresent()) {
            throw new IllegalArgumentException("Profile with this ID already exists");
        }
        try {
            profileRepository.save(profile);
            profile.sendProfileCreatedEvent();
            profileRepository.save(profile);
            return Optional.of(profile);
        } catch (Exception e) {
            throw new RuntimeException("Error saving profile: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command, String profileId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.updateProfile(command);
        try {
            profileRepository.save(profile);
            return Optional.of(profile);
        } catch (Exception e) {
            throw new RuntimeException("Error updating profile: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ClassSchedule> handle(CreateClassScheduleCommand command, String profileId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        var classSchedule = profile.addClassScheduleToAcademicInformation(command);
        try {
            profileRepository.save(profile);
            return Optional.of(classSchedule);
        } catch (Exception e) {
            throw new RuntimeException("Error saving class schedule: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<FavoriteStop> handle(CreateFavoriteStopCommand command, String profileId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.addFavoriteStop(command);
        profileRepository.save(profile);
        return profile.getFavoriteStops()
                .stream()
                .filter(favoriteStop -> favoriteStop.getName().equals(command.name()))
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
    public Optional<FavoriteStop> handle(UpdateFavoriteStopCommand command, String profileId, String favoriteStopId) {
        UserId userId = new UserId(profileId);
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        Optional<FavoriteStop> updatedFavoriteStop = profile.updateFavoriteStop(favoriteStopId, command);
        profileRepository.save(profile);

        return updatedFavoriteStop;
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

    @Override
    public void handle(DeleteFavoriteStopCommand command) {
        UserId userId = new UserId(command.profileId());
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        boolean removed = profile.removeFavoriteStopByFavoriteStopId(command.favoriteStopId());
        if (!removed) {
            throw new IllegalArgumentException("Favorite stop not found");
        }
        profileRepository.save(profile);
    }

}
