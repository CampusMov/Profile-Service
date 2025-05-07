package com.campusmov.platform.profileservice.profile.application.internal.queryservices;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetClassSchedulesByProfileIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetFavoriteStopsByProfileIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.queries.GetProfileByIdQuery;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileQueryService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        UserId userId = new UserId(query.profileId());
        return profileRepository.findById(userId);
    }

    @Override
    public Optional<List<ClassSchedule>> handle(GetClassSchedulesByProfileIdQuery query) {
        UserId userId = new UserId(query.profileId());

        return profileRepository.findById(userId)
                .map(profile -> profile.getAcademicInformation().getClassSchedules());
    }

}
