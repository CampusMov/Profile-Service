package com.campusmov.platform.profileservice.profile.domain.services;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetClassSchedulesByProfileIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetFavoriteStopsByProfileIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    Optional<List<ClassSchedule>> handle(GetClassSchedulesByProfileIdQuery query);
    Optional<List<FavoriteStop>> handle(GetFavoriteStopsByProfileIdQuery query);
}
