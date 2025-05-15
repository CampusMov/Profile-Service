package com.campusmov.platform.profileservice.profile.domain.services;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.commands.*;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command, String profileId);
    Optional<ClassSchedule> handle(CreateClassScheduleCommand command, String profileId);
    Optional<FavoriteStop> handle(CreateFavoriteStopCommand command, String profileId);
    Optional<ClassSchedule> handle(UpdateClassScheduleCommand command, String profileId, String classScheduleId);
    Optional<FavoriteStop> handle(UpdateFavoriteStopCommand command, String profileId, String favoriteStopId);
    void handle(DeleteClassScheduleCommand command);
    void handle(DeleteFavoriteStopCommand command);
}
