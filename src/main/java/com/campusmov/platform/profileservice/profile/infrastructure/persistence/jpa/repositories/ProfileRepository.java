package com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories;
import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UserId> {

}
