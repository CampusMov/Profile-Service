package com.campusmov.platform.profileservice.profile.domain.model.queries;

public record GetAllClassSchedulesByCourseNameAndProfileId(
        String courseName,
        String profileId
) {
}
